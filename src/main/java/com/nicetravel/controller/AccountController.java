package com.nicetravel.controller;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

//    getAll
    @GetMapping
    public String getAllAccount(Model model){
        List<Account> accounts = accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "/account/index";
    }

//    getById
    @GetMapping("{id}")
    public String getOneAccount(@PathVariable("id") Integer id, Model model){
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "/account/index";
    }

//    getByUserName
    @GetMapping("{username}")
    public String getAccount(@PathVariable("username") String username, Model model){
        Account account = accountService.findAccountsByUsername(username);
        model.addAttribute("account", account);
        return "account/index";
    }


//    Edit
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        List<Account> accounts =accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "/account/form";
    }

//    Update
    @PostMapping("/update")
    public String updateAccount(Account account){
        accountService.updateAccount(account);
        return "redirect:/form";
    }

//    Create
    @GetMapping("/create")
    public String createAcc(@ModelAttribute("account")Account account){
        return "/account/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("account") Account account){
        accountService.createAccount(account);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        accountService.deleteAccount(id);
        return "redirect:/account";
    }

}
