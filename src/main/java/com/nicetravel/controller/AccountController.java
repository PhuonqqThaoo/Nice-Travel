package com.nicetravel.controller;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final UserServices userServices;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    @Autowired
    public AccountController(AccountService accountService, UserServices userServices, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.accountService = accountService;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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
        return "/account/account-detail/info";
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


    //  REGISTER

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new Account());
        return "/account/register/signup_form";
    }

    @PostMapping("/process-register")
    public String processRegister(Account account, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        userServices.register(account, getSiteURL(request));
        return "/account/register/register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        System.out.println(code);
        if (userServices.verify(code)) {
            return "/account/register/verify_success";
        } else {
            return "/account/register/verify_fail";
        }
    }


//    Forgot password


    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "/account/forgot/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(64);

        try {
            userServices.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            userServices.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "/account/forgot/forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account account = accountService.findByVerificationCode(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "/account/forgot/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Account account = accountService.findByVerificationCode(token);
        model.addAttribute("title", "Reset your password");

        if (account == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userServices.updatePassword(account, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }

}
