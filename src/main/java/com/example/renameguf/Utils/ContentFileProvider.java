package com.example.renameguf.Utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface ContentFileProvider {
    List<String> getContentFilesList (List<File> fileList);
}
