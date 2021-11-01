package com.nicetravel.controller;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final UserServices userServices;

    private final AccountService accountService;

    @Autowired
    public SecurityController(UserServices userServices, AccountService accountService) {
        this.userServices = userServices;
        this.accountService = accountService;
    }
//    LOGIN

    @GetMapping("/login")
    public String loginForm(Model model) {
//        model.addAttribute("message","Please login");
        model.addAttribute("account", new Account());
        return "/nice_travel/form-in-up";
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

// REGISTER
@GetMapping("/register")
public String showRegistrationForm(Model model) {
    model.addAttribute("account", new Account());
    return "/nice_travel/form-in-up";
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


//    FORGOT

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
        Account account = userServices.getByResetPasswordToken(token);
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

        Account account = userServices.getByResetPasswordToken(token);
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
