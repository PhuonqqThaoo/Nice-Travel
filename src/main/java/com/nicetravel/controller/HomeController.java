package com.nicetravel.controller;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private final AccountService accountService;

    @Autowired
    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping({"/", "/index", "/home"})
    public String index(Model model, HttpServletRequest request){
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("account", account);
        return "forward:/travel/list";
    }
    @RequestMapping("/trang-chu")
    public String avatar(Model model, HttpServletRequest request){
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("userRequest", account);
        return "layout/_header";
    }
}
