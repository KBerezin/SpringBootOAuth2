package ru.javamentor.OAuth2Practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied() {
        return "access_denied";
    }
}