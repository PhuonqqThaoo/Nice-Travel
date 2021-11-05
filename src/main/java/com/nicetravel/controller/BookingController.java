package com.nicetravel.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.entity.Travel;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("booking")
public class BookingController {
	@Autowired
	TravelService travelService;
	@Autowired
	AccountService accountService;
	@Autowired
	BookingService bookingService;
	@Autowired
	BookingDetailService bookingDetailService;

	@RequestMapping("check/{id}")
	public String check(Model model, HttpServletRequest request,@PathVariable("id") Integer id) {
		String username = request.getRemoteUser();
		model.addAttribute("travel",travelService.findTravelById(id));
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "booking/check";
	}
	
	@RequestMapping("thanhtoan")
	public String submit(HttpServletRequest request,@RequestParam("id") Integer id){
		String username = request.getRemoteUser();
		Account accountId = accountService.findAccountsByUsername(username);
		Travel travelId = travelService.findTravelById(id);
		Booking booking = new Booking();
		Date now = new Date();
		Timestamp timestamp = new Timestamp(now.getTime());
		//tạo đơn hàng
		booking.setCreatedDate(timestamp);
		booking.setAccountId(accountId);
		booking.setPayBoolean(true);
		booking.setPhone(accountId.getPhone());
		booking.setIsDeleted(false);
		booking.setTotalPrice(travelId.getPrice());
		
		BookingDetail bDetail = new BookingDetail();
		bDetail.setBookingId(booking);
		bDetail.setPrice(booking.getTotalPrice());
		bDetail.setTravelId(travelId);
		
		bookingService.createBooking(booking);
		bookingDetailService.createBookingDetail(bDetail);
		
		return "redirect:/";
	}
}
