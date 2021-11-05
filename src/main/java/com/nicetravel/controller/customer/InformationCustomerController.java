package com.nicetravel.controller.customer;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer")
public class InformationCustomerController {

	private final AccountService accountService;

	@Autowired
	public InformationCustomerController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/information-customer")
	public String getInformationCustomer(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "customer/InformationCustomer";
	}
	
	@GetMapping("/edit-information-customer")
	public String getEditInformationCustomer(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "/customer/EditInformationCustomer";
	}

	@PostMapping("/update")
	public String update(Model model, @ModelAttribute("account") Account account,HttpServletRequest request) {
		accountService.createAccount(account);
		return "forward:/edit-information-customer";
	}

	@GetMapping("/change-password")
	public String getChangePassword(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("pass", accountService.findAccountsByUsername(username));
		return "customer/ChangePassword";
	}
}
