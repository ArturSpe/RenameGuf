package com.example.renameguf.View;

import com.example.renameguf.View.Component.InputFieldsComponent;

import java.util.Map;

public interface MainWindow <I> {
    Map<InputFieldsComponent, I> getValueFields();
    void clearField();

    void blockButton();
    void unlockButton();

}
