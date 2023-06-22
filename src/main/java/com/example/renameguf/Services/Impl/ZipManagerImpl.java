package com.example.renameguf.Services.Impl;

import com.example.renameguf.Services.PacketManager;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipManagerImpl implements PacketManager<ZipOutputStream> {
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
}
