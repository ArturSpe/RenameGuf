package com.example.renameguf.Services.Impl;

import com.example.renameguf.Utils.Impl.JsonFileProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/*Так как в методе getJsonNodesList() получение json'а происходит при помощи библиотеки Jackson думаю нет смысла тестировать
непосредственно преобразование, при этом есть смысл количество полученных жсонов после преобразования
 */
@SpringBootTest(classes = {JsonFileProviderImpl.class})
class JsonFileProviderImplTest {

    private final String path = "path";

    @Autowired
    private JsonFileProviderImpl jsonFileHandler;

    private List<String> stringList;
    private final int amountJson = 5;

    @Test
    void getJsonNodesList() {

        Assertions.assertEquals(amountJson, jsonFileHandler.getJsonNodesList(stringList).size());
    }

    @BeforeEach
    private void startMethod (){
        int i = amountJson;
        stringList = new ArrayList<>();
        while (i != 0){
            stringList.add("{\"jsonNode\" : 1}");
            i--;
        }
    }
}