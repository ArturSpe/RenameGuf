package com.example.renameguf.View;

import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.GufRenamePanel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface PanelWithFields extends GufRenamePanel {
    Map<InputFieldsComponent, String> getFields();
    void clearField ();
}
