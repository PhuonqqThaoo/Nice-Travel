package com.nicetravel.controller.customer;

import java.awt.print.Book;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.custom.UserServices;
import com.nicetravel.custom.Utility;
import com.nicetravel.entity.EventTour;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;

import static com.nicetravel.custom.Utility.getSiteURL;

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

	@RequestMapping("/isDelete/{id}")
	public String isDelete(@PathVariable("id") Integer id) {
		Booking booking = bookingService.findById(id);
		booking.setIsDeleted(true);
		bookingService.updateBooking(booking);
		
		return "redirect:/customer/tour-da-dat";
	}


//	Hủy Tour

	@GetMapping("/cancel-tour/{id}")
	public String cancelTour(@PathVariable("id") Integer id, Model model) {
		Booking booking = bookingService.findById(id);
		model.addAttribute("booking", booking);
		model.addAttribute("message", "Bạn có chắc muốn hủy tour");
//		model.addAttribute("events", new EventTour());
		return "/customer/CancelTour";
	}

	@PostMapping("/process-cancel-tour")
	public String processCancelTour(Booking booking, EventTour eventTour, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {

		userServices.cancelTour(booking, eventTour, getSiteURLBooking(request));
		return "/customer/CancelTour";
	}

	private String getSiteURLBooking(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}




}
