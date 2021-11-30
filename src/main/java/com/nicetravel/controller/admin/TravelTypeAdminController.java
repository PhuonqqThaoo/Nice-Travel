package com.nicetravel.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/admin/travel-type")
public class TravelTypeAdminController {
	private final AccountService accountService;
	
	@Autowired
	public TravelTypeAdminController( AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping("")
	public String getTravelType(Model model, HttpServletRequest request) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		return "admin/quan-ly/tour-du-lich/QuanLy-TravelType";
	}
}
