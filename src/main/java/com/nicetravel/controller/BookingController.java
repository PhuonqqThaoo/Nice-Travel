package com.nicetravel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.custom.UserServices;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.Travel;
import com.nicetravel.security.auth.CustomOAuth2User;
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

	@Autowired
	UserServices userServices;

	@GetMapping(value = "/check")
	public String check(Model model, HttpServletRequest request, @RequestParam("tour") String slug, Authentication authentication) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = null;

		if (account == null){
			CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
			Account accountOauth = accountService.findByEmail(oauth2User.getEmail());
			username = accountOauth.getUsername();
		}
		else {
			username = account.getUsername();
		}
		
		List<Travel> listFavo = travelService.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);

		System.out.println("user: " + accountService.findAccountsByUsername(username));
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		model.addAttribute("travel", travelService.findTravelBySlug(slug));
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		System.out.println(username.toString());
		return "booking/check2";
	}

}
