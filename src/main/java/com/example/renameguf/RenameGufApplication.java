package com.example.renameguf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class  RenameGufApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RenameGufApplication.class)
                .headless(false)
                .run(args);
    }

}
