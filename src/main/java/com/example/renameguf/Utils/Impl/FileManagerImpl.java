package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.FileManager;
import com.example.renameguf.Utils.SqlManager;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileManagerImpl implements FileManager<ZipOutputStream> {

    private final SqlManager sqlManager;

    public FileManagerImpl (SqlManager sqlManager){
        this.sqlManager = sqlManager;
    }
    @Override
    public ZipOutputStream createPacket(String path) throws FileNotFoundException {
        return new ZipOutputStream(new FileOutputStream(path + ".zip"));
    }

    @Override
    public void createFileInPack(ZipOutputStream zip, File file, String name) {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(name);
            zip.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zip.write(buffer, 0, bytesRead);
            }
            zip.closeEntry();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createSqlRequest(String path, String name, List<String> listUuid, String host) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + name + ".txt"))) {
            writer.write(sqlManager.createUuidSqlRequest(listUuid.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")), host));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
