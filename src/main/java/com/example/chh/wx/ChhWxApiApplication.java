package com.example.chh.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;



@SpringBootApplication
@ServletComponentScan
public class ChhWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChhWxApiApplication.class, args);
    }


}
