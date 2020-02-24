package com.monteiro.virtualclassroom.virtualclassroom;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


//on ne touche pas

@SpringBootApplication
public class VirtualclassroomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VirtualclassroomApplication.class, args);

    }
}
