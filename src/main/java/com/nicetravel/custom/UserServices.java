package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.RoleRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public List<Account> listAll() {
        return accountRepository.findAll();
    }

    public void register(Account account, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        account.setVerificationCode(randomCode);
        account.setIsEnable(false);

        accountRepository.save(account);

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
        Account account = accountRepository.findByVerificationCode(verificationCode);

        if (account == null || account.getIsEnable()) {
            return false;
        } else {
            account.setVerificationCode(null);
            account.setIsEnable(true);
            account.setRole_Id(roleRepository.findByRoleName("USER"));
            accountRepository.save(account);

            return true;
        }

    }

}
