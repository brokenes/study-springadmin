package com.github.admin.api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
