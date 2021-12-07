package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.EventTour;
import com.nicetravel.entity.Provider;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.BookingRepository;
import com.nicetravel.repository.RoleRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.EventsService;
import com.nicetravel.service.RoleService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventsService eventsService;

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
                + "Vui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn:<br>"
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
            account.setIsEnable(false);
            account.setRole_Id(roleService.findByRoleName("USER"));
            account.setImg("user.png");
            account.setProvider(Provider.DATABASE);
            accountService.createAccount(account);

            return true;
        }

    }

//
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
        accountService.updateAccount(account);
    }

//    ChangePassword

    public void changePassword(Account account, String newPassword) throws Exception {
        account.setPassword(newPassword);

        account.setPasswordChangedTime(new Date());

        accountService.update(account);
    }

    //    export excel
    public List<Account> listAllAccounts() {
        return accountRepository.findAll(Sort.by("id").ascending());
    }


// HỦY TOUR
    public void cancelTour(Booking booking, EventTour eventTour, String siteURL) throws UnsupportedEncodingException, MessagingException{
        System.out.println("booking: " + booking);
//        eventsService.createEvent(eventTour);
        String random = RandomString.make(64);
        booking.setVerification_code(random);
        booking.setCreatedDate(booking.getCreatedDate());
        booking.setTotalPrice(booking.getTotalPrice());
        booking.setPayBoolean(booking.getPayBoolean());
        booking.setPhone(booking.getPhone());
        booking.setTotalPrice(booking.getTotalPrice());
        booking.setIsDeleted(false);
        bookingService.updateBooking(booking);
        System.out.println("booking1: " + booking);
        senMailCancelTour(booking, siteURL);

    }


    private void senMailCancelTour(Booking booking, String siteURL) throws MessagingException, UnsupportedEncodingException{

        String toAddress = booking.getBooking_account_id().getEmail();;
        String fromAddress = "nicetravelcompany@gmail.com";
        String senderName = "Nice Travel Company";
        String subject = "Yêu cầu hủy tour từ bạn";
        String content = "Thân chào <b>[[name]]</b>,<br>"
                + "Bạn có chắc muốn hủy tour đã chọn:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">XÁC NHẬN</a></h3>"
                + "Cảm ơn bạn,<br>"
                + "Nice Travel.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", booking.getBooking_account_id().getUsername());
        String verifyURL = siteURL + "/verify?code=" + booking.getVerification_code();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email đã được gửi");
    }

    public boolean verifyCancelTour(String verificationCode) {
        System.out.println("verifyCancel");
        Booking booking = bookingService.findByVerificationCode(verificationCode);
//        System.out.println("verify code: " + booking);
        if (booking == null) {
            System.out.println("update fail");
            return false;
        } else {
            booking.setVerification_code(null);
            booking.setCreatedDate(booking.getCreatedDate());
            booking.setTotalPrice(booking.getTotalPrice());
            booking.setPayBoolean(booking.getPayBoolean());
            booking.setTotalPrice(booking.getTotalPrice());
            booking.setPhone(booking.getPhone());
            booking.setIsDeleted(true);
            bookingService.updateBooking(booking);
            System.out.println("update success");

            return true;
        }

    }

}