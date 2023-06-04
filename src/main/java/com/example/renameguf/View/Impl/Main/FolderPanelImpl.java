package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.PanelWithFields;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Component("Folder")
public class FolderPanelImpl extends JTextArea implements PanelWithFields {

    private final FolderTransferHandler folderTransferHandler;

    public FolderPanelImpl(FolderTransferHandler folderTransferHandler){
        super(InputFieldsComponent.PathToFolder.getValue());
        this.folderTransferHandler = folderTransferHandler;
        setEditable(false);
        setLineWrap(true);
        setMargin(new Insets(20, 20, 20, 20));
        setTransferHandler(folderTransferHandler);
    }

    @Override
    public Map<InputFieldsComponent, String> getFields() {
        Map<InputFieldsComponent, String> fieldsStringMap = new HashMap<>();
        fieldsStringMap.put(InputFieldsComponent.PathToFolder, folderTransferHandler.getPathToFolder());
        return fieldsStringMap;
    }

    @Override
    public void clearField() {
        setText(InputFieldsComponent.PathToFolder.getValue());
    }
}
