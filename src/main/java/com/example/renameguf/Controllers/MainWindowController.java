package com.example.renameguf.Controllers;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import com.example.renameguf.Services.CheckListCreator;
import com.example.renameguf.Services.PackageCreator;
import com.example.renameguf.Services.SeekerForbidden;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class MainWindowController {

    private final SeekerForbidden seekerForbidden;
    private final PackageCreator packageCreator;

    private final CheckListCreator checkListCreator;

    public MainWindowController(SeekerForbidden seekerForbidden, PackageCreator packageCreator, CheckListCreator checkListCreator) {
        this.seekerForbidden = seekerForbidden;
        this.packageCreator = packageCreator;
        this.checkListCreator = checkListCreator;
    }

    public void renameGuf (FieldsGuf fieldsGuf) {

        try {
            packageCreator.createPacket(fieldsGuf);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public List<GufDib> checkDIB (FieldsGuf fieldsGuf){
        return seekerForbidden.checkDib(fieldsGuf);

    }

    public List<GufDib> checkEmail (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return seekerForbidden.checkEmail(fieldsGuf);

    }

    public List<GufDib> checkLogins (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return seekerForbidden.checkLogins(fieldsGuf);

    }
    public List<GufDib> checkTokens (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return seekerForbidden.checkTokens(fieldsGuf);

    }

    public List<GufDib> checkOrder (FieldsGuf fieldsGuf) throws IOException, URISyntaxException, InterruptedException {
        return seekerForbidden.checkMissGuf(fieldsGuf);
    }

    public void createCheckList (FieldsGuf fieldsGuf) throws URISyntaxException, IOException {
        checkListCreator.createCheckList(fieldsGuf);
    }

    public void CancelProcess () {
        seekerForbidden.CancelProcess();
    }

}
