package com.example.renameguf.Services;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.ZipOutputStream;

@Component
public interface ZipManager {
    ZipOutputStream createZipArchive (String path) throws FileNotFoundException;
    void createFileInZip (ZipOutputStream zip, File file, String name);

}
