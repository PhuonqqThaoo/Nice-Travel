package com.nicetravel.controller.staff;

import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/staff")
public class HomeStaffContrller {

	@Autowired
	AccountService accountService;

	@GetMapping("")
	public String getIndex(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "staff/index";
	}
}
