package com.nicetravel.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/admin")
public class ThongKeTourBookingAdminController {
	private final AccountService accountService;
	@Autowired
	public ThongKeTourBookingAdminController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping("/thongke-tourbooking")
	public String getThongKeTourBooking(HttpServletRequest request, Model model) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		
		return "admin/thong-ke/ThongKe-Booking";
	}
}
