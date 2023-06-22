package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.Handler.MainWindowHandler.Event;
import com.example.renameguf.View.Component.ButtonComponent;
import com.example.renameguf.View.GufRenamePanel;
import com.example.renameguf.View.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("Button")
public class ButtonPanelImpl extends JPanel implements GufRenamePanel {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationContext applicationContext;

    private final List <JButton> buttonList = new ArrayList<>();

    public ButtonPanelImpl (ApplicationEventPublisher applicationEventPublisher, ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.applicationEventPublisher = applicationEventPublisher;
        setLayout(new GridLayout(5, 1, 10, 10));
        setButton();

    }

    @Override
    public java.awt.Component add(java.awt.Component comp) {
        buttonList.add((JButton) comp);
        return super.add(comp);
    }

    private void setButton (){
        JButton renameButton = new JButton(ButtonComponent.Rename.getValue());
        renameButton.addActionListener(e -> {
            Event.ClickOnRenameEvent clickOnRenameEvent = new Event.ClickOnRenameEvent(new HashMap<>());
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
            applicationEventPublisher.publishEvent(new Event.ClickOnDIBEvent(new Object()));
        });
        add(DIBButton);

        JButton checkEmailButton = new JButton(ButtonComponent.CheckEmail.getValue());
        checkEmailButton.addActionListener(e -> {
            System.out.println("Нажатие");
            applicationEventPublisher.publishEvent(new Event.ClickOnEmailEvent(new HashMap<>()));
        });
        add(checkEmailButton);

        JButton checkLoginsButton = new JButton(ButtonComponent.CheckLogins.getValue());
        checkLoginsButton.addActionListener(e -> {
            System.out.println("Нажатие");
            applicationEventPublisher.publishEvent(new Event.ClickOnLoginsEvent(new HashMap<>()));
        });
        add(checkLoginsButton);
    }

    void lockButtons () {
        buttonList.forEach(x ->x.setEnabled(false));
    }

    void unlockButtons() {
        buttonList.forEach(x ->x.setEnabled(true));
    }

}
