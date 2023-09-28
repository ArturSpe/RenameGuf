package com.example.renameguf.Services.Impl;

import com.example.renameguf.Utils.Impl.ContentFileProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest  (classes = {ContentFileProviderImpl.class})
class ContentFileHandlerImplTest {
    private List<File> fileList;

    @Autowired
    private ContentFileProviderImpl contentFileHandler;
    @Test
    public void ContentFileHandlerImpl () {


        for (String stringContent : contentFileHandler.getContentFilesList(fileList)){
            Assertions.assertTrue(stringContent.contains("{testField1 : \"test\"}"));
        }

    }
    @BeforeEach
    public void start(){
        File file1 = new File("src/test/resources/rguf1.guf");
        File file2 = new File("src/test/resources/rguf2.guf");
        fileList = new ArrayList<>(List.of(new File[]{file1, file2}));
    }
}