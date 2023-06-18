package com.example.renameguf.Services.Impl;

import com.example.renameguf.Model.GufDib;
import com.example.renameguf.Services.FileHandler;
import com.example.renameguf.Services.GufFileHandler;
import com.example.renameguf.Services.PacketManager;
import com.example.renameguf.Model.FieldsGuf;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Component
public class GufFileHandlerImpl implements GufFileHandler {

    private final PacketManager<ZipOutputStream> zipManager;
    private final ExecutorService executorService;
    private final FileHandler fileHandler;

    public GufFileHandlerImpl (PacketManager<ZipOutputStream> zipManager, @Qualifier("DibTask") ExecutorService executorService, FileHandler fileHandler){
        this.fileHandler = fileHandler;
        this.executorService = executorService;
        this.zipManager = zipManager;
    }
    public List<String> createPacket(FieldsGuf fieldsGuf) throws IOException {

        List<String> listNewNameGufs = new ArrayList<>();

        String prefixName = new StringBuilder()
                .append(fieldsGuf.getIdentCommand())
                .append("_")
                .append(fieldsGuf.getNumberTaskJira())
                .append("v")
                .append(fieldsGuf.getGufVersion())
                .toString();

        ZipOutputStream zipWithGufs = zipManager.createPacket(fieldsGuf.getPathToFolder() +
                "\\" + fieldsGuf.getNumberTaskJira() +
                "v"+ fieldsGuf.getGufVersion());

        for (File file : fileHandler.getListFileFromFolder(fieldsGuf.getPathToFolder())) {
            if (file.getName().endsWith(".guf")) {
                String name = file.getName();
                int i = name.indexOf("_");
                String nameWithOutNumber = name.substring(i + 1);

                Double numberGuf = Double.parseDouble(name.substring(0, i));
                String numberGufStringWithZeros = "";
                DecimalFormat decimalFormat = new DecimalFormat("000000");
                if (numberGuf % 1 != 0) {
                    decimalFormat = new DecimalFormat("000000.0");
                }
                numberGufStringWithZeros = decimalFormat.format(numberGuf);
                String newName = new StringBuilder()
                        .append(numberGufStringWithZeros)
                        .append("_")
                        .append(prefixName)
                        .append("_")
                        .append(nameWithOutNumber)
                        .toString();

                zipManager.createFileInPack(zipWithGufs, file, newName);
                listNewNameGufs.add(newName);
            }
        }
        zipWithGufs.close();
        return listNewNameGufs;
    }

    @Override
    public List<GufDib> checkDib(FieldsGuf fieldsGuf) {

        List<GufDib> gufWithLogin = new ArrayList<>();
        List<File> fileList = fileHandler.getListFileFromFolder(fieldsGuf.getPathToFolder());

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

        List<File> files = fileHandler.getListFileFromFolder(fieldsGuf.getPathToFolder());
        List<String> domainsList = fileHandler.getListStringFromTxt("Domains");
        List<GufDib> gufWithLogin = new ArrayList<>();

        int DomainsSize = domainsList.size();
        fieldsGuf.getCreateProgress().accept(files.size() * DomainsSize);

        CountDownLatch countDownLatch = new CountDownLatch(DomainsSize);
        for (String domain : domainsList){
            GufCheckEmailProcessor gufCheckEmailProcessor = GufCheckEmailProcessor.builder()
                    .updateBar(fieldsGuf.getUpdateBar())
                    .searchWord(domain)
                    .listGuf(files)
                    .countDownLatch(countDownLatch)
                    .listDibGuf(gufWithLogin)
                    .build();
            executorService.submit(gufCheckEmailProcessor);
        }

        countDownLatch.await();
        return gufWithLogin;
    }

    @Override
    public List<GufDib> checkLogins(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {


        List<File> files = fileHandler.getListFileFromFolder(fieldsGuf.getPathToFolder());
        List<String> loginsList = fileHandler.getListStringFromTxt("Logins");
        List<GufDib> gufWithLogin = new ArrayList<>();

        int loginsSize = loginsList.size();
        fieldsGuf.getCreateProgress().accept(files.size() * loginsSize);

        CountDownLatch countDownLatch = new CountDownLatch(loginsSize);
        for (String domain : loginsList){
            GufCheckEmailProcessor gufCheckEmailProcessor = GufCheckEmailProcessor.builder()
                    .updateBar(fieldsGuf.getUpdateBar())
                    .searchWord(domain)
                    .listGuf(files)
                    .countDownLatch(countDownLatch)
                    .listDibGuf(gufWithLogin)
                    .build();
            executorService.submit(gufCheckEmailProcessor);
        }
        countDownLatch.await();
        return gufWithLogin;
    }

    @Override
    public void CancelProcess() {
        executorService.shutdownNow();
    }
}
//Добавить проверки на то, что файлы соответствуют типу и имени и то, что путь - это папка
