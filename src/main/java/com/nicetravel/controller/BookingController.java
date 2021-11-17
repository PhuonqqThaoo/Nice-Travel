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

	@RequestMapping("check")
	public String check(Model model, HttpServletRequest request, @RequestParam("tour") String slug) {
		String username = request.getRemoteUser();
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		model.addAttribute("travel", travelService.findTravelBySlug(slug));
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "booking/check";
	}
}
