package com.example.renameguf.View.Handler.MainWindowHandler;

import com.example.renameguf.View.Handler.ClickButton;
import org.springframework.context.ApplicationEvent;

public class Event  {

    public static class ClickOnDIBEvent extends ApplicationEvent implements ClickButton {
        public ClickOnDIBEvent(Object source) {
            super(source);
        }
    }

    public static class ClickOnRenameEvent extends ApplicationEvent implements ClickButton {
        public ClickOnRenameEvent(Object source) {
            super(source);
        }
    }

    public static class ClickOnLoginsEvent extends ApplicationEvent implements ClickButton {
        public ClickOnLoginsEvent(Object source) {
            super(source);
        }
    }

    public static class ClickOnEmailEvent extends ApplicationEvent implements ClickButton {
        public ClickOnEmailEvent(Object source) {
            super(source);
        }
    }
    public static class ClickOnTokenButton extends ApplicationEvent implements ClickButton {
        public ClickOnTokenButton(Object source) {
            super(source);
        }
    }
    public static class ClickOnMissButton extends ApplicationEvent implements ClickButton {
        public ClickOnMissButton(Object source) {
            super(source);
        }
    }

    public static class ClickOnEgorButton extends ApplicationEvent implements ClickButton {
        public ClickOnEgorButton(Object source) {super(source);}
    }

}
