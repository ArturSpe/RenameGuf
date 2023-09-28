package com.example.renameguf.Services;

import com.example.renameguf.Model.FieldsGuf;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public interface CheckListCreator {

    void createCheckList (FieldsGuf fieldsGuf) throws URISyntaxException, IOException;
}
