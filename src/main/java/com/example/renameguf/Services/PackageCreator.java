package com.example.renameguf.Services;

import com.example.renameguf.Model.FieldsGuf;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface PackageCreator {

    List<String> createPacket(FieldsGuf fieldsGuf) throws IOException;

}
