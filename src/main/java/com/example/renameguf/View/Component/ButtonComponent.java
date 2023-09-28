package com.example.renameguf.View.Component;

public enum ButtonComponent {
    Rename ("Создать пакет"),
    DIB ("Проверка подстрок"),
    Clear ("Очистить поля"),
    CheckEmail("Проверить на почту (по списку Domain)"),
    CheckTokens("Найти упоминания токенов"),
    CheckLogins ("Проверить по списку Logins(400 записей - Рекомендуются пакеты с размером < 50)"),

    CheckMissGuf ("Проверить порядок гуфов"),

    CheckListDib("Чек - лист для Егора");

    private final String value;
    ButtonComponent(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
