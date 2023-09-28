package com.example.renameguf.View.Impl.Main;

import com.example.renameguf.View.Handler.MainWindowHandler.Event;
import com.example.renameguf.View.Component.ButtonComponent;
import com.example.renameguf.View.GufRenamePanel;
import com.example.renameguf.View.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

@Component("Button")
public class ButtonPanelImpl extends JPanel implements GufRenamePanel {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationContext applicationContext;
    private final List <JButton> buttonList = new ArrayList<>();
    private final Map<ButtonComponent, ApplicationEvent> applicationEventMap;
    public ButtonPanelImpl (ApplicationEventPublisher applicationEventPublisher, ApplicationContext applicationContext){

        this.applicationContext = applicationContext;
        this.applicationEventPublisher = applicationEventPublisher;
        setLayout(new GridLayout(8, 1, 10, 10));

        applicationEventMap = new LinkedHashMap<>();
        applicationEventMap.put(ButtonComponent.Rename, new Event.ClickOnRenameEvent(new HashMap<>()));
        applicationEventMap.put(ButtonComponent.DIB, new Event.ClickOnDIBEvent(new Object()));
        applicationEventMap.put(ButtonComponent.CheckEmail, new Event.ClickOnEmailEvent(new HashMap<>()));
        applicationEventMap.put(ButtonComponent.CheckLogins, new Event.ClickOnLoginsEvent(new HashMap<>()));
        applicationEventMap.put(ButtonComponent.CheckTokens, new Event.ClickOnTokenButton(new HashMap<>()));
        applicationEventMap.put(ButtonComponent.CheckListDib, new Event.ClickOnEgorButton(new HashMap<>()));
        applicationEventMap.put(ButtonComponent.CheckMissGuf, new Event.ClickOnMissButton(new Object()));

        setButton();
    }

    @Override
    public java.awt.Component add(java.awt.Component comp) {
        buttonList.add((JButton) comp);
        return super.add(comp);
    }

    private void setButton (){

        for (Map.Entry<ButtonComponent, ApplicationEvent> entry : applicationEventMap.entrySet()){
            add(new ButtonGufHandler(entry, applicationEventPublisher));
        }

        JButton clearButton = new JButton(ButtonComponent.Clear.getValue());
        clearButton.addActionListener(e -> {
            applicationContext.getBeansOfType(MainWindow.class).forEach((key, value) -> value.clearField());
        });
        add(clearButton);

    }

    void lockButtons () {
        buttonList.forEach(x ->x.setEnabled(false));
    }

    void unlockButtons() {
        buttonList.forEach(x ->x.setEnabled(true));
    }

    private static class ButtonGufHandler extends JButton {
        ButtonGufHandler (Map.Entry<ButtonComponent, ApplicationEvent> buttonEntry, ApplicationEventPublisher applicationEventPublisher){
            super(buttonEntry.getKey().getValue());
            this.addActionListener(e -> {
                applicationEventPublisher.publishEvent(buttonEntry.getValue());
            });

        }
    }

}
