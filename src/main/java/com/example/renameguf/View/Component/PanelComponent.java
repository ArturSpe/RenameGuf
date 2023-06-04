package com.example.renameguf.View.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum PanelComponent {
    Folder("Folder"),
    Fields("Fields"),
    Button("Button");
    private final String value;
    private static final Map<PanelComponent, String> mapLocation;

    static {
        mapLocation = new HashMap<>();
        mapLocation.put(Folder, BorderLayout.NORTH);
        mapLocation.put(Fields, BorderLayout.CENTER);
        mapLocation.put(Button, BorderLayout.SOUTH);

    }

    PanelComponent(String value) {
        this.value = value;
    }

    public static String getLocation(PanelComponent panel){
        return mapLocation.get(panel);
    }
    public String getValue(){
        return value;
    }

}
