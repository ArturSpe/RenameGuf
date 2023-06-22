package com.example.renameguf.Services;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public interface GufFileHandler {
    List<String> createPacket(FieldsGuf fieldsGuf) throws IOException;
    List<GufDib> checkDib(FieldsGuf fieldsGuf);

    List<GufDib> checkEmail(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;

    List<GufDib> checkLogins(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;

    void CancelProcess ();

}
