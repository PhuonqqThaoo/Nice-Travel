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
import com.nicetravel.entity.TravelLike;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelLikeService;

@Controller
@RequestMapping("/customer")
public class HomeCustomerController {
	@Autowired
	TravelLikeService travelLikeService;
	@Autowired
	AccountService accountService;
	
	@GetMapping("")
	public String getIndex(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
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
