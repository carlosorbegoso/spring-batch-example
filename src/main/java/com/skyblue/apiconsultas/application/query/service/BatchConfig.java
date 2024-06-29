package com.skyblue.apiconsultas.application.query.service;

import com.skyblue.apiconsultas.data.SunatData;
import com.skyblue.apiconsultas.data.SunatDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final SunatDataRepository sunatDataRepository;


    @Bean
    public FlatFileItemReader<SunatData> itemReader(){
        FlatFileItemReader<SunatData> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/padron_reducido_ruc.txt"));
        itemReader.setName("SunatData-Reader");
        itemReader.setLinesToSkip(1);


        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }
    @Bean
    public  SunatDataProcessor itemProcessor(){
        return new SunatDataProcessor();
    }

@Bean
public RepositoryItemWriter<SunatData> write(){
    RepositoryItemWriter<SunatData> writer = new RepositoryItemWriter<>();
    writer.setRepository(sunatDataRepository);
    writer.setMethodName("save");
    return writer;
}

    @Bean
    public Step step1(){
            return  new StepBuilder( "txtImport", jobRepository)
                    .<SunatData,SunatData>chunk(1000,platformTransactionManager)
                    .reader(itemReader())
                    .processor(itemProcessor())
                    .writer(write())
                    .build();
    }
    @Bean
    public Job runJob(){
        return new JobBuilder("importDataSunat",jobRepository)
                .start(step1())
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }



    private LineMapper<SunatData> lineMapper(){
        DefaultLineMapper<SunatData> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("ruc", "nombreRazonSocial", "estadoContribuyente", "condicionDomicilio", "ubigeo", "tipoVia", "nombreVia", "codigoZona", "tipoZona", "numero", "interior", "lote", "departamento", "manzana", "kilometro");

        BeanWrapperFieldSetMapper<SunatData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(SunatData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return  lineMapper;
    }
}
