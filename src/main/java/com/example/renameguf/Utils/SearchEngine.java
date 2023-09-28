package com.example.renameguf.Utils;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Model.GufDib;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface SearchEngine {

    List<GufDib> search (List<File> GufList, FieldsGuf fieldsGuf, List<String> keyWordList) throws InterruptedException;
}
