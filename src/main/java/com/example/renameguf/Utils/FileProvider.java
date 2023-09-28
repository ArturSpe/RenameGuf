package com.example.renameguf.Utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Component
public interface FileProvider {
    List<File> getFiles(String path, boolean isRecursive);
    Map<String, List<File>> getMapFolderListFile (String path, boolean isRecursive);

}
