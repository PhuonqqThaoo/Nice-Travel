package com.nicetravel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
public class AboutController {

    private final AccountService accountService;

    @Autowired
    public AboutController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @RequestMapping({"/about"})
    public String about(Model model, HttpServletRequest request){
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("account", account);
        return "about/about";
    }
}
