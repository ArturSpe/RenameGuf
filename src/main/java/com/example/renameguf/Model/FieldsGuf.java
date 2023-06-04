package com.example.renameguf.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class FieldsGuf {
    private final String pathToFolder;
    private final String identCommand;
    private final String numberTaskJira;
    private final String gufVersion;
    private final String loginUser;

    @Override
    public String toString() {
        return "FieldsGuf{" +
                "pathToFolder='" + pathToFolder + '\'' +
                ", identCommand='" + identCommand + '\'' +
                ", numberTaskJira='" + numberTaskJira + '\'' +
                ", gufVersion='" + gufVersion + '\'' +
                ", loginUser='" + loginUser + '\'' +
                '}';
    }
}
