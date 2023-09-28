package com.example.renameguf.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class GufDib {
    private final File guf;
    private final int numberString;
    private final String trigger;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GufDib gufDib = (GufDib) o;
        return numberString == gufDib.numberString && Objects.equals(guf, gufDib.guf);
    }
    @Override
    public int hashCode() {
        return Objects.hash(guf, numberString);
    }
}
