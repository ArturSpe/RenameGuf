package com.example.renameguf.View;

import org.springframework.stereotype.Component;

@Component
public interface ProgressFrame {
    void createBar (int size);
    void closeBar ();
    void updateBar ();

}
