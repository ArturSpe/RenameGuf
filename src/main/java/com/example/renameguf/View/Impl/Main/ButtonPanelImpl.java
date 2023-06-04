package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.Listener.MainWindowListeners.ClickOnDIBEvent;
import com.example.renameguf.Listener.MainWindowListeners.ClickOnRenameEvent;
import com.example.renameguf.View.Component.ButtonComponent;
import com.example.renameguf.View.GufRenamePanel;
import com.example.renameguf.View.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@Component("Button")
public class ButtonPanelImpl extends JPanel implements GufRenamePanel {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationContext applicationContext;

    public ButtonPanelImpl (ApplicationEventPublisher applicationEventPublisher, ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.applicationEventPublisher = applicationEventPublisher;
        setLayout(new GridLayout(3, 1, 10, 10));
        setButton();

    }
    private void setButton (){
        JButton renameButton = new JButton(ButtonComponent.Rename.getValue());
        renameButton.addActionListener(e -> {
            ClickOnRenameEvent clickOnRenameEvent = new ClickOnRenameEvent(new HashMap<>());
            applicationEventPublisher.publishEvent(clickOnRenameEvent);
        });
        add(renameButton);

        JButton clearButton = new JButton(ButtonComponent.Clear.getValue());
        clearButton.addActionListener(e -> {
            applicationContext.getBeansOfType(MainWindow.class).forEach((key, value) -> value.clearField());
        });
        add(clearButton);

        JButton DIBButton = new JButton(ButtonComponent.DIB.getValue());
        DIBButton.addActionListener(e -> {
            applicationEventPublisher.publishEvent(new ClickOnDIBEvent(new HashMap<>()));
        });
        add(DIBButton);
    }

}
