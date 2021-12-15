package com.nicetravel.security.auth;

import com.nicetravel.custom.UserService;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.Provider;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import com.paypal.base.codec.binary.Base64;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class OAuthLoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    private final AccountService accountService;

    private final RoleService roleService;

    private final JavaMailSender mailSender;

//    private BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public OAuthLoginSuccessHandle(UserService userService, AccountService accountService, RoleService roleService, JavaMailSender mailSender) {
        this.userService = userService;
        this.accountService = accountService;
        this.roleService = roleService;
        this.mailSender = mailSender;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        String random = Double.toString(Math.round(Math.random() * 9 + 1));

        String randomPass = RandomString.make(8);

        System.out.println("randomPass: " + randomPass);

        String encodePass = getPasswordEncoder().encode(randomPass);

        System.out.println("oauth: " + oauth2User.getAttributes());

        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String email = oauth2User.getEmail();
        String username = oauth2User.getName();
        String picture = oauth2User.getPicture();
        String fullName = oauth2User.getFullName();

        Account accountByEmail = accountService.findByEmail(email);

        System.out.println("accountByEmail: " + accountByEmail);

        if (accountByEmail != null) {
            System.out.println("Tài khoản người dùng đã tồn tại trong cơ sở dữ liệu.");
        }
        else {
            System.out.println("Người dùng mới đã được thêm vào.");
            Account newAccount = new Account();

            if(oauth2ClientName.equals("Facebook")){
                newAccount.setImg("user.png");
            }
            else {
                newAccount.setImg(picture);
            }
            newAccount.setFullname(fullName);
            newAccount.setUsername(email);
            newAccount.setPassword(encodePass);
            newAccount.setEmail(email);
            newAccount.setGender(false);
            newAccount.setAddress("");
            newAccount.setPhone("");
            newAccount.setId_Card("Vui long nhap lai ID Card " + random);
            newAccount.setRole_Id(roleService.findByRoleName("USER"));
            newAccount.setVerificationCode("");
            newAccount.setProvider(Provider.valueOf(oauth2ClientName.toUpperCase()));
            newAccount.setIsEnable(false);
            accountService.createAccount(newAccount);

            userService.updateAuthenticationType(username, oauth2ClientName);

//            gui mail

            String toAddress = email;;
            String fromAddress = "nicetravelcompany@gmail.com";
            String senderName = "Nice Travel Company";
            String subject = "Mật khẩu của bạn";
            String content = "Thân chào <b>[[name]]</b>,<br>"
                    + "Đây là mật khẩu đăng nhập với email của bạn:<br>"
                    + "<h3>[[PASS]]</h3>"
                    + "Cảm ơn bạn,<br>"
                    + "Nice Travel.";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            content = content.replace("[[name]]",username);

            content = content.replace("[[PASS]]", randomPass);

            try {
                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                helper.setText(content, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            mailSender.send(message);

            System.out.println("Email đã được gửi");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }


}
