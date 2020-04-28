package ru.javamentor.OAuth2Practice.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class UserController {

    public UserController() {
    }

    @GetMapping(value = "/users")
    public String showUsers() {
        return "users";
    }
}
