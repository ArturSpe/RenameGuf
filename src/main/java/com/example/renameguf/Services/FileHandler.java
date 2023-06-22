package com.example.renameguf.Services;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public interface FileHandler {
    List<File> getListFileFromFolder (String path);
    List<String> getListStringFromTxt (String name) throws URISyntaxException, IOException;
}
