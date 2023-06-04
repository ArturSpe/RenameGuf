package com.example.renameguf.Listener.MainWindowListeners;

import com.example.renameguf.Listener.ClickButton;
import org.springframework.context.ApplicationEvent;

public class ClickOnDIBEvent extends ApplicationEvent implements ClickButton {
    public ClickOnDIBEvent(Object source) {
        super(source);
    }
}
