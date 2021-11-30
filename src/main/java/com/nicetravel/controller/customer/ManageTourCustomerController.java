package com.nicetravel.controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/tour-da-dat")
	public String getManageTour(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		List<Booking> items = bookingService.getAllBookingByAcId(username);
		model.addAttribute("items", items);
		return "customer/TourDaDat";
	}
	@RequestMapping("/isDelete/{id}")
	public String isDelete(@PathVariable("id") Integer id) {
		Booking booking = bookingService.findById(id);
		booking.setIsDeleted(true);
		bookingService.updateBooking(booking);
		
		return "redirect:/customer/tour-da-dat";
	}
}
