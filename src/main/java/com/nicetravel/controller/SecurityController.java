package com.nicetravel.controller;

import com.nicetravel.custom.UserServices;
import com.nicetravel.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class SecurityController {

//    LOGIN

    @RequestMapping("/login")
    public String loginForm(Model model) {
//        model.addAttribute("message","Please login");
        return "/account/login/login";
    }

    @RequestMapping("/login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Login success");
        return "forward:/login";
    }

    @RequestMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Login fail");
        return "forward:/login";
    }

    @RequestMapping("/unauthorized")
    public String unauthoried(Model model) {
        model.addAttribute("message", "You're unauthorized");
        return "forward:/login";
    }

//    LOGOUT

    @RequestMapping("/logoff/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Logout success");
        return "forward:/login";
    }
}
