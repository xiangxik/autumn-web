package com.autumn;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AutumnWebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AutumnWebApplication.class).run(args);
    }

}
