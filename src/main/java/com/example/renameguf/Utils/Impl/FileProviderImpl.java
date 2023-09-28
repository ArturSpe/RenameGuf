package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.FileProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FileProviderImpl implements FileProvider {

    private final ClassLoader classLoader = FileProviderImpl.class.getClassLoader();
    private final Map<String, List<File>> listMap = new HashMap<>();

    @Override
    public List<File> getFiles(String path, boolean isRecursive) {
        return getMapFolderListFile(path, isRecursive).values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<File>> getMapFolderListFile(String path, boolean isRecursive) {

        Path directoryPath = Paths.get(path);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path path1 : directoryStream) {
                File file = path1.toFile();
                if (file.isDirectory() && isRecursive) {
                    getMapFolderListFile(file.getAbsolutePath(), true);
                } else {
                    if (listMap.containsKey(String.valueOf(directoryPath))){
                        listMap.get(String.valueOf(directoryPath)).add(file);
                    }else{
                        List<File> fileList = new ArrayList<>();
                        fileList.add(file);
                        listMap.put(String.valueOf(directoryPath), fileList);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listMap;
    }

}
