package com.example.renameguf.Controllers;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import com.example.renameguf.Services.GufFileHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class MainWindowController {

    GufFileHandler gufFileHandler;

    public MainWindowController (GufFileHandler gufFileHandler){
        this.gufFileHandler = gufFileHandler;
    }

    public void renameGuf (FieldsGuf fieldsGuf) {

        try {
            gufFileHandler.createPacket(fieldsGuf);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public List<GufDib> checkDIB (FieldsGuf fieldsGuf){
        return gufFileHandler.checkDib(fieldsGuf);

    }

    public List<GufDib> checkEmail (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return gufFileHandler.checkEmail(fieldsGuf);

    }

    public List<GufDib> checkLogins (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return gufFileHandler.checkLogins(fieldsGuf);

    }

    public void CancelProcess () {
        gufFileHandler.CancelProcess();
    }

}
