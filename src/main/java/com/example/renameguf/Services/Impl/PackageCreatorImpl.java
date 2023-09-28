package com.example.renameguf.Services.Impl;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Services.PackageCreator;
import com.example.renameguf.Utils.FileManager;
import com.example.renameguf.Utils.FileProvider;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Component
public class PackageCreatorImpl implements PackageCreator {

    private final FileManager<ZipOutputStream> fileManager;
    private final FileProvider fileProvider;

    public PackageCreatorImpl (FileManager<ZipOutputStream> fileManager, FileProvider fileProvider){
        this.fileManager = fileManager;
        this.fileProvider = fileProvider;
    }

    @Override
    public List<String> createPacket(FieldsGuf fieldsGuf) throws IOException {
        List<String> listNewNameGufs = new ArrayList<>();

        String prefixName = new StringBuilder()
                .append(fieldsGuf.getIdentCommand())
                .append("_")
                .append(fieldsGuf.getNumberTaskJira())
                .append("v")
                .append(fieldsGuf.getGufVersion())
                .toString();

        ZipOutputStream zipWithGufs = fileManager.createPacket(fieldsGuf.getPathToFolder() +
                "\\" + fieldsGuf.getNumberTaskJira() +
                "v"+ fieldsGuf.getGufVersion());

        for (File file : fileProvider.getFiles(fieldsGuf.getPathToFolder(), false)) {
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

                fileManager.createFileInPack(zipWithGufs, file, newName);
                listNewNameGufs.add(newName);
            }
        }
        zipWithGufs.close();
        return listNewNameGufs;
    }
}
