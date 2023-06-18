package com.example.renameguf.Services.Impl;

import com.example.renameguf.Services.FileHandler;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FileHandlerDesktop implements FileHandler {

    private final ClassLoader classLoader = FileHandlerDesktop.class.getClassLoader();

    @Override
    public List<File> getListFileFromFolder(String path){
        Path directoryPath = Paths.get(path);
        List<File> fileList = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path path1 : directoryStream) {
                fileList.add(path1.toFile());
            }
        } catch (IOException ex) {
             ex.printStackTrace();
        }
        return fileList;
    }

    @Override
    public List<String> getListStringFromTxt(String name) {

        List<String> listDomain = null;

//        try {
//            listDomain = Files.lines(Paths.get(Objects.requireNonNull(classLoader.getResource(name)).toURI())).toList();
//        }catch (URISyntaxException | IOException exception){
//            exception.printStackTrace();
//        }


        File jarFile = new File(GufFileHandlerImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File domainsFile = new File(jarFile.getParentFile().getAbsolutePath() + File.separator + name);
        try (BufferedReader reader = new BufferedReader(new FileReader(domainsFile))) {
            listDomain = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            listDomain = new ArrayList<>();
        }
        return listDomain;

    }
}
