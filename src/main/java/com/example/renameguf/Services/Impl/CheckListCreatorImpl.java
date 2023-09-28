package com.example.renameguf.Services.Impl;

import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.Services.CheckListCreator;
import com.example.renameguf.Utils.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CheckListCreatorImpl implements CheckListCreator {

    private final JsonFileProvider jsonFileProvider;
    private final ContentFileProvider contentFileProvider;
    private final FileProvider fileProvider;

    private final LinesProvider linesProvider;

    private final FileManager fileManager;

    public CheckListCreatorImpl(JsonFileProvider jsonFileProvider, ContentFileProvider contentFileProvider,
                                FileProvider fileProvider, LinesProvider linesProvider,
                                FileManager fileManager) {
        this.jsonFileProvider = jsonFileProvider;
        this.contentFileProvider = contentFileProvider;
        this.fileProvider = fileProvider;
        this.linesProvider = linesProvider;
        this.fileManager = fileManager;
    }

    @Override
    public void createCheckList(FieldsGuf fieldsGuf) throws URISyntaxException, IOException {
        Map<DesiredNodes, Set<String>> desiredNodesListMap = DesiredNodes.getMap();

        List<JsonNode> contentFilesList = jsonFileProvider.
                getJsonNodesList(contentFileProvider.
                        getContentFilesList(fileProvider.
                                getFiles(fieldsGuf.
                                        getPathToFolder(), true)));

        fieldsGuf.getCreateProgress().accept(DesiredNodes.values().length * contentFilesList.size());

        for (JsonNode guf : contentFilesList) {
            JsonNode objectInfoNode = guf.get("objectInfo");
            Iterator<Map.Entry<String, JsonNode>> jsonNodeEntry = objectInfoNode.fields();

            while (jsonNodeEntry.hasNext()) {
                Map.Entry<String, JsonNode> entry = jsonNodeEntry.next();
                JsonNode typeNameNode = objectInfoNode.get(entry.getKey()).get("typeName");
                System.out.println(typeNameNode);
                for (DesiredNodes desiredNode : DesiredNodes.values()) {
                    fieldsGuf.getUpdateBar().run();
                    if (typeNameNode.asText().toLowerCase().indexOf(desiredNode.getValue().toLowerCase()) == 0) {
                        desiredNodesListMap.get(desiredNode).add("'" + entry.getKey() + "'");
                    }
                }

            }
        }
        for (Map.Entry <DesiredNodes, Set<String>> entry : desiredNodesListMap.entrySet()) {

            System.out.println(linesProvider.getLinesFromTxt("host"));
            if (entry.getValue().size() != 0) {
                fileManager.createSqlRequest(fieldsGuf.getPathToFolder(),
                        entry.getKey().name(),
                        entry.getValue().stream().toList(),
                        linesProvider.getLinesFromTxt("host").get(0));
            }
        }
    }
}
