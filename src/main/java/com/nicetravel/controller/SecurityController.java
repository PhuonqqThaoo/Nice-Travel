package com.nicetravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping("/login/form")
    public String loginForm(Model model) {
//        model.addAttribute("message","Please login");
        return "security/login";
    }

    @RequestMapping("/login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Login success");
//        return "/travel/list";
        return "forward:/security/login/form";
    }

//    @RequestMapping("/registration/form")
//    public String registrationForm(Model model) {
//        model.addAttribute("message", "Đăng ký thành công");
//        return "security/registration";
//    }

    @RequestMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Login fail");
        return "forward:/security/login/form";
    }

    @RequestMapping("/unauthorized")
    public String unauthoried(Model model) {
        model.addAttribute("message", "You're unauthorized");
        return "forward:/security/login/form";
    }

    @RequestMapping("/logoff/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Logout success");
        return "forward:/security/login/form";
    }


    @RequestMapping("/logout")
    public String logout(){
        return "security/logout";
    }

}
