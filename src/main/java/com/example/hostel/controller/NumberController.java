package com.example.hostel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NumberController {

    @GetMapping("/numbers")
    public String home(Model model) {
        model.addAttribute("title", "Номера");
        return "numbers";
    }
}