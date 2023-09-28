package com.example.renameguf.Services;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public interface SeekerForbidden {

    List<GufDib> checkDib(FieldsGuf fieldsGuf);

    List<GufDib> checkEmail(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;

    List<GufDib> checkLogins(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;

    List<GufDib> checkTokens(FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;
    List <GufDib> checkMissGuf (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException;


    void CancelProcess ();

}
