package com.example.renameguf.Listener.MainWindowListeners;

import com.example.renameguf.Listener.ClickButton;
import org.springframework.context.ApplicationEvent;


public class ClickOnRenameEvent extends ApplicationEvent implements ClickButton {
    public ClickOnRenameEvent(Object source) {
        super(source);
    }
}
