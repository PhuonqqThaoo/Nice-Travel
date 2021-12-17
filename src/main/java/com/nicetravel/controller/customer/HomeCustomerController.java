package com.nicetravel.controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nicetravel.security.auth.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelLikeService;

@Controller
@RequestMapping("/customer")
public class HomeCustomerController {
	@Autowired
	TravelLikeService travelLikeService;
	@Autowired
	AccountService accountService;
	@Autowired
	BookingService bookingService;


	
	@GetMapping("")
	public String getIndex(Model model, HttpServletRequest request, Authentication authentication) {

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
		
		List<Booking> booking = bookingService.getAllBookingByAcId(username);
		model.addAttribute("booking", booking);
		Account accountId = accountService.findAccountsByUsername(username);
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		List<TravelLike> items = travelLikeService.getAllTravelLikeByIdAcount(accountId);
		model.addAttribute("items", items);
		return "customer/index";
	}
	
	@RequestMapping("delete/{id}")
	public String deleteLike(@PathVariable("id") Integer id) {
		travelLikeService.deleteTravelLike(id);
		return "redirect:/customer";
	}
}
