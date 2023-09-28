package com.example.renameguf.Services.Impl;

import java.util.*;

public enum DesiredNodes {

    AccessGroup ("Access group"),
    Algorithm ("Алгоритм."),
    DataSource ("Источник данных на"),
    Script ("Скрипт");

    private final String value;
    DesiredNodes(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Map<DesiredNodes, Set<String>> getMap (){
        Map <DesiredNodes, Set<String>> desiredNodesListMap = new HashMap<>();
        for (DesiredNodes node : DesiredNodes.values()){
            desiredNodesListMap.put(node, new HashSet<>());
        }
        return desiredNodesListMap;
    }

}
