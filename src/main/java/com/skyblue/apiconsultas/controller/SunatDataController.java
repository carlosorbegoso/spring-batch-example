package com.skyblue.apiconsultas.controller;

import com.skyblue.apiconsultas.dto.Message;
import com.skyblue.apiconsultas.service.SunatQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SunatDataController {

    private final SunatQueryService sunatQueryService;

    public SunatDataController(SunatQueryService sunatQueryService) {
        this.sunatQueryService = sunatQueryService;
    }

    public Message getSunatData() throws IOException, InterruptedException {

        return sunatQueryService.saveSunatInLots();
    }
    @GetMapping("/sunat/count")
    public Long getSunatDataCount() throws IOException {
        return sunatQueryService.countLinesInFile();
    }

}