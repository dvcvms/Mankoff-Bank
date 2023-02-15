package ru.evsmanko.mankoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping("/user")
    public String userInfo() {
        return "info";
    }
    @GetMapping("/balans")
    public String balances() {
        return "balances";
    }
}
