package com.example.renameguf.Listener.MainWindowListeners;

import com.example.renameguf.Controllers.MainWindowController;
import com.example.renameguf.Model.FieldsGuf;
import com.example.renameguf.View.Component.InputFieldsComponent;
import com.example.renameguf.View.DibCheckView;
import com.example.renameguf.View.MainWindow;
import com.example.renameguf.View.PanelWithFields;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MainWindowListener {
    private final MainWindowController mainWindowController;
    private final MainWindow<String> mainWindow;

    private final DibCheckView dibCheckView;

    public MainWindowListener(MainWindowController mainWindowController, MainWindow<String> mainWindow, DibCheckView dibCheckView){
        this.mainWindow = mainWindow;
        this.mainWindowController = mainWindowController;
        this.dibCheckView = dibCheckView;
    }

    @EventListener(ClickOnRenameEvent.class)
    public void clickOnRenameEvent() {
        Map<InputFieldsComponent, String> inputFieldsComponentStringMap = mainWindow.getValueFields();
        FieldsGuf fieldsGuf = FieldsGuf.builder()
                .pathToFolder(inputFieldsComponentStringMap.get(InputFieldsComponent.PathToFolder))
                .identCommand(inputFieldsComponentStringMap.get(InputFieldsComponent.CommandIdent))
                .numberTaskJira(inputFieldsComponentStringMap.get(InputFieldsComponent.NumberTaskJira))
                .gufVersion(inputFieldsComponentStringMap.get(InputFieldsComponent.GufVersion))
                .build();

        mainWindowController.renameGuf(fieldsGuf);
    }

    @EventListener(ClickOnDIBEvent.class)
    public void clickOnDIBEvent(){
        FieldsGuf fieldsGuf = FieldsGuf.builder()
                .pathToFolder(mainWindow.getValueFields().get(InputFieldsComponent.PathToFolder))
                .loginUser(mainWindow.getValueFields().get(InputFieldsComponent.LoginUserCheck))
                .build();

        dibCheckView.showFiles(mainWindowController.checkDIB(fieldsGuf));
    }
}
