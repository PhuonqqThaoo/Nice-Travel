package com.nicetravel.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.nicetravel.custom.UserServices;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.entity.Travel;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@Autowired
	UserServices userServices;

	@GetMapping(value = "/check")
	public String check(Model model, HttpServletRequest request, @RequestParam("tour") String slug) {
		String username = request.getRemoteUser();
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		model.addAttribute("travel", travelService.findTravelBySlug(slug));
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "booking/check";
	}

}
