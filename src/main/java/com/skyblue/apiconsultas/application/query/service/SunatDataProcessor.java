package com.skyblue.apiconsultas.application.query.service;

import com.skyblue.apiconsultas.data.SunatData;
import org.springframework.batch.item.ItemProcessor;

public class SunatDataProcessor implements ItemProcessor<SunatData,SunatData> {
    @Override
    public SunatData process(SunatData sunatData) throws Exception {
        //all the bussines logic goes here
        return sunatData;
    }
}
