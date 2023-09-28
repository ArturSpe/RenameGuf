package com.example.renameguf.View.Component;

public enum InputFieldsComponent {
    PathToFolder("Перетащите папку сюда..."),
    CommandIdent("Индент Команды (прим. CKP_KO)"),
    NumberTaskJira("Номер задачи в Жире (прим. CKP-1234)"),
    GufVersion("Версия Гуфа (прим. 1)"),
    Preview ("Предпросмотр"),
    LoginUserCheck ("Поиск подстрок (для проверки ДИБ)");
    private final String value;
    InputFieldsComponent(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
