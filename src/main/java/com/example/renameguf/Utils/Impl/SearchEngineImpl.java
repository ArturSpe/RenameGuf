package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import com.example.renameguf.Utils.SearchEngine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Component
public class SearchEngineImpl implements SearchEngine {

    private final ExecutorService executorService;

    public SearchEngineImpl(@Qualifier("DibTask")ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public List<GufDib> search(List<File> GufList, FieldsGuf fieldsGuf, List<String> keyWordList) throws InterruptedException {

        Set<GufDib> gufWithLogin = new HashSet<>();
        int DomainsSize = keyWordList.size();
        fieldsGuf.getCreateProgress().accept(GufList.size() * DomainsSize);

        CountDownLatch countDownLatch = new CountDownLatch(DomainsSize);
        for (String domain : keyWordList){
            GufCheckEmailProcessor gufCheckEmailProcessor = GufCheckEmailProcessor.builder()
                    .updateBar(fieldsGuf.getUpdateBar())
                    .searchWord(domain)
                    .listGuf(GufList)
                    .countDownLatch(countDownLatch)
                    .listDibGuf(gufWithLogin)
                    .build();
            executorService.submit(gufCheckEmailProcessor);
        }

        countDownLatch.await();
        return gufWithLogin.stream().toList();
    }
}

