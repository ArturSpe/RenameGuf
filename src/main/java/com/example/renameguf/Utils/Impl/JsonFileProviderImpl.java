package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.JsonFileProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileProviderImpl implements JsonFileProvider {

    @Override
    public List<JsonNode> getJsonNodesList(List<String> stringList) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodeList = new ArrayList<>();
        for (String s : stringList){

            try {
                jsonNodeList.add(objectMapper.readTree(s));
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

        }
        return jsonNodeList;
    }
}
