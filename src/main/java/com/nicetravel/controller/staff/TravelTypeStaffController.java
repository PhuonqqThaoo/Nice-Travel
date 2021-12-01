package com.nicetravel.controller.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/staff/travel-type")
public class TravelTypeStaffController {
	private final AccountService accountService;
	
	@Autowired
	public TravelTypeStaffController( AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping("")
	public String getTravelType(Model model, HttpServletRequest request) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		return "staff/quan-ly/tour-du-lich/QuanLy-TravelType";
	}
}
