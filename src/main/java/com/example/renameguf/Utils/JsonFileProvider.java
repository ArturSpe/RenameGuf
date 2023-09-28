package com.example.renameguf.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JsonFileProvider {
    List<JsonNode> getJsonNodesList(List <String> stringList);
}
