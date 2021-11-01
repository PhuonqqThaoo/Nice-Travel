package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.RoleRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import com.nicetravel.service.impl.AccountServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Transactional
public class UserServices {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public List<Account> listAll() {
        return accountService.getAllAccount();
    }

    public void register(Account account, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        account.setVerificationCode(randomCode);
        account.setIsEnable(false);

        accountService.createAccount(account);

        sendVerificationEmail(account, siteURL);
    }

    private void sendVerificationEmail(Account account, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = account.getEmail();
        String fromAddress = "nicetravelcompany@gmail.com";
        String senderName = "Nice Travel Company";
        String subject = "Please verify your registration";
        String content = "Dear <b>[[name]]</b>,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Nice Travel.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", account.getUsername());
        String verifyURL = siteURL + "/verify?code=" + account.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }

    public boolean verify(String verificationCode) {
        Account account = accountService.findByVerificationCode(verificationCode);

        if (account == null || account.getIsEnable()) {
            return false;
        } else {
            account.setVerificationCode(null);
            account.setIsEnable(true);
            account.setRole_Id(roleService.findByRoleName("USER"));
            accountService.createAccount(account);

            return true;
        }

    }

//    -------------------------------------

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email);
        if (account != null) {
            account.setVerificationCode(token);
            accountService.createAccount(account);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public Account getByResetPasswordToken(String token) {
        return accountService.findByVerificationCode(token);
    }

    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);

        account.setVerificationCode(null);
        accountService.createAccount(account);
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("nicetravelcompany@gmail.com", "NiceTravel Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}