package com.example.renameguf.Controllers;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Services.GufFileHandler;
import org.springframework.stereotype.Component;

import java.io.File;
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

    public List<File> checkDIB (FieldsGuf fieldsGuf){
        return gufFileHandler.checkDib(fieldsGuf);

    }

}
