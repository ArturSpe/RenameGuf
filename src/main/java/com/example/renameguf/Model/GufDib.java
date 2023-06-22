package com.example.renameguf.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
@Builder
@AllArgsConstructor
public class GufDib {
    private final File guf;
    private final int numberString;
    private final String trigger;
}
