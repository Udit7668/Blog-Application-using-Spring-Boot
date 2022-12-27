package com.udit.blogapplication.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FormController {
    
    @GetMapping("/login")
    public String login(){
    return "login";
    }

    @GetMapping("/register")
    public String register(){
    return "sign-up";
    }

    @GetMapping("/createUser")
    public String createUser(@RequestBody User user){
        System.out.println(user);
        return "";

    }
}