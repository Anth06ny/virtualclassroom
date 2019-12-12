package com.monteiro.virtualclassroom.virtualclassroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VirtualclassroomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VirtualclassroomApplication.class, args);
    }
}
