package com.example.zipcrypt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    String hello(){
        return "hello world";
    }
}