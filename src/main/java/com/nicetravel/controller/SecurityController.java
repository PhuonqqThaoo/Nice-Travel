package com.nicetravel.controller;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class SecurityController {

    private final UserServices userServices;

    private final AccountService accountService;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public SecurityController(UserServices userServices, AccountService accountService) {
        this.userServices = userServices;
        this.accountService = accountService;
    }
//    LOGIN

    @GetMapping("/login")
    public String loginForm(Model model) {
//        model.addAttribute("message","Please login");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "/nice_travel/form-in-up";
//        }

        model.addAttribute("account", new Account());
        return "/nice_travel/form-in-up";
    }

    @RequestMapping("/login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Đăng nhập thành công");
        return "forward:/login";
    }

    @RequestMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Đăng nhập không thành công");
        return "forward:/login";
    }

    @RequestMapping("/unauthorized")
    public String unauthoried(Model model) {
        model.addAttribute("message", "Bạn không có quyền");
        return "forward:/login";
    }

//    LOGOUT

    @RequestMapping("/logoff/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
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
    public String verifyUser(@Param("code") String code, Model model) {
        System.out.println(code);
        if (userServices.verify(code)) {
            model.addAttribute("message", "Xác thực tài khoản thành công");
            return "forward:/login";
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
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Một liên kết đặt lại mật khẩu đã gửi đến email của bạn. Vui lòng kiểm tra hộp thư.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi gửi email");
        }

        return "/account/forgot/forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("nicetravelcompany@gmail.com", "NiceTravel Support");
        helper.setTo(recipientEmail);

        String subject = "Đặt lại mật khẩu của bạn";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình..</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>"
                + "<h3><a href=\"" + link + "\">Thay đổi mật khẩu</a></h3>"
                + "<p><strong>Lưu ý: </strong>Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu.</p>"
                + "Cảm ơn bạn,<br>"
                + "Nice Travel.";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Account account = userServices.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (account == null) {
            model.addAttribute("message", "Mã Token không hợp lệ");
            return "/account/forgot/forgot_password_form";
        }

        return "/account/forgot/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) throws Exception {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        System.out.println("token: " + token);

        System.out.println("pass: " + password);

        Account account = userServices.getByResetPasswordToken(token);
        model.addAttribute("title", "Đặt lại mật khẩu của bạn");

        System.out.println("account by token: " + account);

        if (account == null) {
            model.addAttribute("message", "Mã Token không hợp lệ");
            return "/account/forgot/forgot_password_form";
        }
        else{
            userServices.updatePassword(account, password);
            System.out.println("updated Password: " + account.getPassword() );

            model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công.");

        }

        return "/account/forgot/forgot_password_form";
    }
}
