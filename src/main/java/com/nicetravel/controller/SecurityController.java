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
import java.util.List;

@Controller
@RequestMapping("/security")
public class SecurityController {

//    LOGIN

    @RequestMapping("/login/form")
    public String loginForm(Model model) {
//        model.addAttribute("message","Please login");
        return "security/login";
    }

    @RequestMapping("/login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Login success");
        return "forward:/security/login/form";
    }

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

//    LOGOUT

    @RequestMapping("/logoff/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Logout success");
        return "forward:/security/login/form";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "security/logout";
    }

//  REGISTER

    @Autowired
    private UserServices service;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "/account/signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(Account account, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        service.register(account, getSiteURL(request));
        return "/verifyEmail/register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Account> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "/verifyEmail/users";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "/verifyEmail/verify_success";
        } else {
            return "/verifyEmail/verify_fail";
        }
    }
}
