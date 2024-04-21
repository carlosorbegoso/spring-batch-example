package com.skyblue.apiconsultas.service;

import com.skyblue.apiconsultas.data.SunatData;
import com.skyblue.apiconsultas.data.SunatDataRepository;
import com.skyblue.apiconsultas.dto.Message;
import com.skyblue.apiconsultas.dto.SunatRuc;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Service
public class SunatQueryService {
    private final ResourceLoader resourceLoader;
    private final SunatDataRepository sunatDataRepository;
    private final MeterRegistry meterRegistry;

    public SunatQueryService(ResourceLoader resourceLoader,
                             SunatDataRepository sunatDataRepository, MeterRegistry meterRegistry) {
        this.resourceLoader = resourceLoader;
        this.sunatDataRepository = sunatDataRepository;
        this.meterRegistry = meterRegistry;
    }


    public Message saveSunatInLots() throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        List<SunatRuc> sunatRucs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(resourceLoader.getResource("classpath:padron_reducido_ruc.txt").getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sunatRucs.add(new SunatRuc(line.split("\\|")));
                if (sunatRucs.size() == 10000) {
                    processBatch(sunatRucs, executor);
                    sunatRucs.clear();
                }
            }
        }

        if (!sunatRucs.isEmpty()) {
            processBatch(sunatRucs, executor);
        }

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.MINUTES);

        return new Message("Data saved");
    }

    private void processBatch(List<SunatRuc> sunatRucs, ExecutorService executor) {
        if (!validateExistenceListRuc(sunatRucs.stream().map(SunatRuc::ruc).collect(Collectors.toList()))) {
            CompletableFuture.runAsync(() -> {
                try {
                    List<SunatData> sunatDataList = sunatRucs.stream()
                            .filter(sunatRuc -> {
                                Optional<SunatData> optionalSunatData = sunatDataRepository.findByRuc(sunatRuc.ruc());
                                if (optionalSunatData.isPresent()) {
                                    boolean exists = sunatDataRepository.existsByRuc(sunatRuc.ruc());
                                    return !exists || sunatRuc.hasChanges(optionalSunatData.get());
                                }
                                return false;
                            })
                            .map(SunatRuc::toEntity)
                            .collect(Collectors.toList());
                    sunatDataRepository.saveAll(sunatDataList);
                } catch (Exception e) {
                    // Handle the exception here. For example, you can log the exception:
                    System.err.println("An error occurred while processing a batch: " + e.getMessage());
                }
            }, executor);
        }
    }
    @Cacheable("ruc")
    public Boolean validateExistenceListRuc(List<String> ruc){
        List<String> existingRucs = sunatDataRepository.findAllByRucIn(ruc);
        return new HashSet<>(existingRucs).containsAll(ruc);
    }
    public long countLinesInFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(resourceLoader.getResource("classpath:padron_reducido_ruc.txt").getFile()))) {
            return br.lines().count();
        }
    }
}