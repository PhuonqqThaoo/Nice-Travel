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
import java.util.Date;
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
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String content = "Thân chào <b>[[name]]</b>,<br>"
                + "PVui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">XÁC NHẬN</a></h3>"
                + "Cảm ơn bạn,<br>"
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

        System.out.println("Email đã được gửi");
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
            accountService.updateAccount(account);
        } else {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản nào có email: " + email);
        }
    }

    public Account getByResetPasswordToken(String token) {
        return accountService.findByVerificationCode(token);
    }

    public void updatePassword(Account account, String newPassword) throws Exception {
String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);

        account.setVerificationCode(null);
        accountService.update(account);
    }


//    ChangePassword
//@Autowired PasswordEncoder passwordEncoder;

    public void changePassword(Account account, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);

        account.setPasswordChangedTime(new Date());

        accountService.createAccount(account);
    }
}