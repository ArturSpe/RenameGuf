package com.example.renameguf.View.Component;

public enum ButtonComponent {
    Rename ("Создать пакет"),
    DIB ("Проверка подстрок"),
    Clear ("Очистить поля"),
    CheckEmail("Проверить на почту (по списку Domain)"),
    CheckLogins ("Проверить по списку Logins(400 записей - Рекомендуются пакеты с размером < 50)");

    private final String value;
    ButtonComponent(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
