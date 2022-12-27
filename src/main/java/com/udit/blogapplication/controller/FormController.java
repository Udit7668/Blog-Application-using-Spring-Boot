package com.udit.blogapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.udit.blogapplication.entities.User;
import com.udit.blogapplication.services.UserService;

@Controller
public class FormController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login(){
    return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
    User user=new User();
    model.addAttribute("users", user);
    return "sign-up";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("users") User user){
        System.out.println(user);
        this.userService.addUser(user);
        return "login";

    }
}