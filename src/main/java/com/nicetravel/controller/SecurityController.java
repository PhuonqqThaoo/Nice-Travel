package com.nicetravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/login/form")
    public String form(){
        return "/security/login";
    }

    @GetMapping("/login/success")
    public String success(){
        return "/security/login";
    }
}
