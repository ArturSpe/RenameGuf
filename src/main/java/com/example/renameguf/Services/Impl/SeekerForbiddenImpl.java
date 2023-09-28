package com.example.renameguf.Services.Impl;

import com.example.renameguf.Model.GufDib;
import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Utils.*;
import com.example.renameguf.Services.SeekerForbidden;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Component
public class SeekerForbiddenImpl implements SeekerForbidden {

    private final FileManager<ZipOutputStream> fileManager;
    private final ExecutorService executorService;
    private final FileProvider fileHandler;

    private final ContentFileProvider contentFileProvider;

    private final JsonFileProvider jsonFileProvider;

    private final SearchEngine searchEngine;

    private final LinesProvider linesProvider;

    public SeekerForbiddenImpl(FileManager<ZipOutputStream> zipManager, @Qualifier("DibTask") ExecutorService executorService,
                               FileProvider fileHandler, ContentFileProvider contentFileProvider, JsonFileProvider jsonFileProvider,
                               SearchEngine searchEngine, LinesProvider linesProvider){
        this.fileHandler = fileHandler;
        this.executorService = executorService;
        this.fileManager = zipManager;
        this.contentFileProvider = contentFileProvider;
        this.jsonFileProvider = jsonFileProvider;
        this.searchEngine = searchEngine;
        this.linesProvider = linesProvider;
    }

    //Переписать с новым методом по получению содержимого файлов
    @Override
    public List<GufDib> checkDib(FieldsGuf fieldsGuf) {

        List<GufDib> gufWithLogin = new ArrayList<>();
        List<File> fileList = fileHandler.getFiles(fieldsGuf.getPathToFolder(), true);
        System.out.println(fileList.size());

        for (File file : fileList) {
            if (file.getName().endsWith(".guf")) {
                try (ZipFile gufFile = new ZipFile(file)){
                    gufFile.stream().forEach(zipEntry -> {
                        try {
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(gufFile.getInputStream(zipEntry)));
                            String line;
                            int i = 0;
                            while ((line = buffer.readLine()) != null) {
                                if (line.contains(fieldsGuf.getLoginUser())) {
                                    gufWithLogin.add(
                                            GufDib.builder()
                                                    .guf(file)
                                                    .numberString(i)
                                                    .trigger(fieldsGuf.getLoginUser())
                                                    .build()
                                    );
                                }
                                i++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    } catch (IOException ex) {
                        ex.printStackTrace();
                }
            }
        }
        return gufWithLogin;
    }

    @Override
    public List<GufDib> checkEmail(FieldsGuf fieldsGuf) throws URISyntaxException, IOException, InterruptedException {
        return searchEngine.search(fileHandler.getFiles(fieldsGuf.getPathToFolder(), true),
                fieldsGuf, linesProvider.getLinesFromTxt("Domains")
                );
    }

    @Override
    public List<GufDib> checkLogins(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return searchEngine.search(fileHandler.getFiles(fieldsGuf.getPathToFolder(), true),
                fieldsGuf, linesProvider.getLinesFromTxt("Logins")
        );
    }

    @Override
    public List<GufDib> checkTokens(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return searchEngine.search(fileHandler.getFiles(fieldsGuf.getPathToFolder(), true),
                fieldsGuf, linesProvider.getLinesFromTxt("Tokens")
        );
    }


    @Override
    public List<GufDib> checkMissGuf(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        Set <GufDib> listFolderWithMissGuf = new HashSet<>();
        for (Map.Entry<String, List<File>> stringListEntry : fileHandler.getMapFolderListFile(fieldsGuf.getPathToFolder(), true).entrySet()){
            List <BigDecimal> doubleList = new ArrayList<>();
            for (File file : stringListEntry.getValue()){
                if (file.getName().endsWith(".guf")){
                    String name = file.getName();
                    int i = name.indexOf("_");
                    name = name.replaceAll(",", ".");
                    String n = name.substring(0, i);
                    System.out.println(stringListEntry.getKey());
                    System.out.println(n);
                    BigDecimal numberGuf = new BigDecimal("0");
                    try {
                        numberGuf = BigDecimal.valueOf(Double.parseDouble(n));
                    }catch (Exception e){
                        System.out.println(e);
                    }

                    doubleList.add(numberGuf);
                    System.out.println(numberGuf);
                }else {
                    listFolderWithMissGuf.add(
                            GufDib
                                    .builder()
                                    .guf(new File(stringListEntry.getKey()))
                                    .numberString(0)
                                    .trigger("Возможно наличие лишнего файла")
                                    .build()
                    );
                }
            }
            Collections.sort(doubleList);
            int indexList = 0;
            for (BigDecimal numberGuf : doubleList){
                BigDecimal trigger;
                indexList++;
                if (indexList != doubleList.size()){
                    boolean isInteger = numberGuf.doubleValue() % 1 == 0;
                    BigDecimal difference = doubleList.get(indexList).subtract(numberGuf);
                    if (isInteger && (
                            difference.equals(new BigDecimal("1.0"))
                            || difference.equals(new BigDecimal("1.1")))
                            || difference.equals(new BigDecimal("0.1"))
                            || difference.equals(new BigDecimal("0.01"))
                            || difference.equals(new BigDecimal("1.01"))
                    ){
                        continue;
                    }else {trigger = difference;}
                    if (!isInteger){
                        BigDecimal differenceFromInteger = doubleList.get(indexList).subtract(BigDecimal.valueOf(Math.floor(numberGuf.doubleValue())));
                        if (differenceFromInteger.equals(new BigDecimal("1.0"))
                                || differenceFromInteger.equals(new BigDecimal("1.1"))
                                || differenceFromInteger.equals(new BigDecimal("0.1"))
                                || differenceFromInteger.equals(new BigDecimal("0.01"))
                                || differenceFromInteger.equals(new BigDecimal("1.01"))
                                || difference.equals(new BigDecimal("0.1"))){
                            continue;
                        }else {trigger = differenceFromInteger;}
                    }
                    else {
                        listFolderWithMissGuf.add(GufDib
                                .builder()
                                        .trigger(trigger.toString())
                                        .guf(new File(stringListEntry.getKey()))
                                        .numberString(numberGuf.intValue())
                                .build());
                    }
                }
            }
        }
        return listFolderWithMissGuf.stream().toList();
    }

    @Override
    public void CancelProcess() {
        executorService.shutdownNow();
    }
}
//Добавить проверки на то, что файлы соответствуют типу и имени и то, что путь - это папка
