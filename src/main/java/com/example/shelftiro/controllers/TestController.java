package com.example.shelftiro.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path = "/hello")
    public String sayHello(){
        return "Hello, db is ok";
    }
}
