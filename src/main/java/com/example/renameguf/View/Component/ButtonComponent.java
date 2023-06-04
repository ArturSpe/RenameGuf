package com.example.renameguf.View.Component;

public enum ButtonComponent {
    Rename ("Создать пакет"),
    DIB ("Проверка на логин"),
    Clear ("Очистить поля");

    private final String value;
    ButtonComponent(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
