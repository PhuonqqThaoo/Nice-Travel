package com.nicetravel.controller.customer;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;

@Controller
@RequestMapping("/customer")
public class ManageTourCustomerController {
	@Autowired 
	BookingService bookingService;
	@Autowired 
	BookingDetailService bookingDetailService;
	@Autowired
	AccountService accountService;

	@Autowired
	UserServices userServices;

	@Autowired
	private JavaMailSender mailSender;

	
	@GetMapping("/tour-da-dat")
	public String getManageTour(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("userRequest", accountService.findAccountsByUsername(username));
		List<Booking> items = bookingService.getAllBookingByAcId(username);
		model.addAttribute("items", items);
		return "/customer/TourDaDat";
	}


	@GetMapping("/cancel-tour")
	public String showForgotPasswordForm() {
		return "/account/forgot/forgot_password_form";
	}

	@PostMapping("/tour-da-dat")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		int id = request.getIntHeader("id");
		String token = RandomString.make(64);

		try {
//			userServices.updateResetPasswordToken(token, id);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(resetPasswordLink);
			model.addAttribute("message", "Một liên kết đặt lại mật khẩu đã gửi đến email của bạn. Vui lòng kiểm tra hộp thư.");

		} catch (UsernameNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Đã xảy ra lỗi khi gửi email");
		}

		return "/customer/TourDaDat";
	}

	public void sendEmail(String link)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("nicetravelcompany@gmail.com", "NiceTravel Support");
		helper.setTo("nicetravelcompany@gmail.com");

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



	@RequestMapping("/isDelete/{id}")
	public String isDelete(@PathVariable("id") Integer id) {
		Booking booking = bookingService.findById(id);
		booking.setIsDeleted(true);
		bookingService.updateBooking(booking);
		
		return "redirect:/customer/tour-da-dat";
	}
}
