package com.boris.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class appController {
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("hello", "Hello World!!!");
        return "hello";
    }

}
