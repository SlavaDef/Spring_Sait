package com.example.websait.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SaitController {

    @GetMapping("/")  // == http://localhost:8080/ -> Hello, Main header!
    public String home(Model model) {
         model.addAttribute( "title", "Main header");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute( "title", "About us");
        return "about";
    }


}
