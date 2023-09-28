package com.example.renameguf;

import org.springframework.context.annotation.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class ConfigClass {

    @Bean
    public JPanel jPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        return jPanel;
    }

    @Bean ("DibTask")
    @Lazy
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ExecutorService executorServiceDib(){
        return Executors.newFixedThreadPool(4);
    }

    @Bean ("ServiceTask")
    @Lazy
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(1);
    }



}
