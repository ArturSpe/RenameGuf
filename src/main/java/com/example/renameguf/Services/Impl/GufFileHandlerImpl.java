package com.example.renameguf.Services.Impl;

import com.example.renameguf.Services.GufFileHandler;
import com.example.renameguf.Services.ZipManager;
import com.example.renameguf.Model.FieldsGuf;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Component
public class GufFileHandlerImpl implements GufFileHandler {

    ZipManager zipManager;

    public GufFileHandlerImpl (ZipManager zipManager){
        this.zipManager = zipManager;
    }
    public List<String> createPacket(FieldsGuf fieldsGuf) throws IOException {


        List<String> listNewNameGufs = new ArrayList<>();
        File folder = new File(fieldsGuf.getPathToFolder());
        File[] files = folder.listFiles();
        assert files != null;

        String prefixName = new StringBuilder()
                .append(fieldsGuf.getIdentCommand())
                .append("_")
                .append(fieldsGuf.getNumberTaskJira())
                .append("_v")
                .append(fieldsGuf.getGufVersion())
                .toString();

        ZipOutputStream zipWithGufs = zipManager.createZipArchive(fieldsGuf.getPathToFolder() +
                "\\" + fieldsGuf.getNumberTaskJira() +
                "v"+ fieldsGuf.getGufVersion());

        for (File file : files) {
            if (file.getName().endsWith(".guf")) {
                String name = file.getName();
                int i = name.indexOf("_");
                String nameWithOutNumber = name.substring(i + 1);

                int numberGuf = Integer.parseInt(name.substring(0, i));
                String numberGufStringWithZeros = String.format("%06d", numberGuf);
                String newName = new StringBuilder()
                        .append(numberGufStringWithZeros)
                        .append("_")
                        .append(prefixName)
                        .append("_")
                        .append(nameWithOutNumber)
                        .toString();

                zipManager.createFileInZip(zipWithGufs, file, newName);
                listNewNameGufs.add(newName);
            }
        }
        zipWithGufs.close();
        return listNewNameGufs;
    }

    @Override
    public List<File> checkDib(FieldsGuf fieldsGuf) {

        File folder = new File(fieldsGuf.getPathToFolder());
        File[] files = folder.listFiles();
        assert files != null;
        List<File> gufWithLogin = new ArrayList<>();

        for (File file : files) {
            if (file.getName().endsWith(".guf")) {
                try (ZipFile gufFile = new ZipFile(file)){
                    gufFile.stream().forEach(zipEntry -> {
                        try {
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(gufFile.getInputStream(zipEntry)));
                            String line;
                            int i = 0;
                            while ((line = buffer.readLine()) != null) {
                                if (line.contains(fieldsGuf.getLoginUser())) {
                                    gufWithLogin.add(file);
                                }
                                i++;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                }
            }
        }
        return gufWithLogin;
    }
}
//Добавить проверки на то, что файлы соответствуют типу и имени и то, что путь - это папка
