package com.example.renameguf.Services;

import com.example.renameguf.Model.FieldsGuf;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public interface GufFileHandler {
    List<String> createPacket(FieldsGuf fieldsGuf) throws IOException;
    List<File> checkDib(FieldsGuf fieldsGuf);

}
