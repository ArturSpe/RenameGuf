package com.example.renameguf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;

@Configuration
@ComponentScan
public class ConfigClass {

    @Bean
    public JPanel jPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        return jPanel;
    }

}
