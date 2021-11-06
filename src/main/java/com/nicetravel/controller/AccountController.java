package com.nicetravel.controller;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

//    getAll
//    @GetMapping
//    public String getAllAccount(Model model){
//        List<Account> accounts = accountService.getAllAccount();
//        model.addAttribute("accounts", accounts);
//        return "/account/index";
//    }
//    @GetMapping
//    public String getAllAccount(){
//        return "/nice_travel/form-in-up";
//    }

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
        List<Account> accounts =accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "/customer/InformationCustomer";
    }


//    Edit
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        List<Account> accounts =accountService.getAllAccount();
        model.addAttribute("accounts", accounts);
        return "/customer/InformationCustomer";
    }


//    Update
    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Integer id, @ModelAttribute("account") Account account){
        Account account1 = accountService.getAccountById(id);
        account.setImg(account1.getImg());
        account.setCreatedDate(account1.getCreatedDate());
        account.setPassword(passwordEncoder.encode(account1.getPassword()));
        account.setRole_Id(account1.getRole_Id());
        account.setIsEnable(account1.getIsEnable());
        accountService.updateAccount(account);
        return "redirect:/account";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        accountService.deleteAccount(id);
        return "redirect:/account";
    }


    @GetMapping("/oauth2/login/success")
    public String success(OAuth2AuthenticationToken oauth){
        String fullname = oauth.getPrincipal().getAttribute("name");

        UserDetails user = User.withUsername(fullname).password("").roles("USER").build();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

}
