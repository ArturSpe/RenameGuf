package com.example.renameguf.Utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

@Component
public interface FileManager<typeOutputStream extends OutputStream> {
    typeOutputStream createPacket(String path) throws FileNotFoundException;
    void createFileInPack(typeOutputStream outputStream, File file, String name);

    void createSqlRequest (String path, String name, List<String> listUuid, String host);

}
