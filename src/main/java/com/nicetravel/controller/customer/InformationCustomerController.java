package com.nicetravel.controller.customer;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	public String getEditInformationCustomer(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/customer/EditInformationCustomer";
	}

	@PostMapping("/edit-information-customer")
	public String update(@Valid @ModelAttribute("userRequest") Account userRequest ,
						 BindingResult result,
						 RedirectAttributes redirect) {
		String errorMessage = null;;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage ="User is not valid";
				redirect.addFlashAttribute("errorMessage", errorMessage);
			}else {
				accountService.update(userRequest);
				String successMessage = "User " + userRequest.getFullname() + " was update";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot update user " + userRequest.getFullname()+" , please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/customer/information-customer";
	}

	@GetMapping("/change-password")
	public String getChangePassword(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/customer/ChangePassword";
	}

	@PostMapping("/change-password")
	public String getChangePassword(@Valid @ModelAttribute("userRequest") Account userRequest ,
									BindingResult result,
									RedirectAttributes redirect) throws Exception {
//		String username = request.getRemoteUser();
//		model.addAttribute("pass", accountService.findAccountsByUsername(username));
		accountService.update(userRequest);
		return "redirect:/customer/information-customer";
	}

}
