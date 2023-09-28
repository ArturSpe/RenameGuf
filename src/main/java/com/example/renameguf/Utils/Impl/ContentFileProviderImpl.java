package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.ContentFileProvider;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
public class ContentFileProviderImpl implements ContentFileProvider {

    @Override
    public List<String> getContentFilesList(List<File> fileList) {
        List <String> stringList = new ArrayList<>();
        for (File file : fileList) {
            try {
                if (file.getName().endsWith(".guf")){
                    try (ZipFile zipFile = new ZipFile(file)) {
                        ZipEntry entry = zipFile.getEntry("update.json");
                        stringList.add(new String(zipFile.getInputStream(entry).readAllBytes(), StandardCharsets.UTF_8));
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return stringList;
    }
}
