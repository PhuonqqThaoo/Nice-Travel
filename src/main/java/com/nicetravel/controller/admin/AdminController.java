package com.nicetravel.controller.admin;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final AccountService accountService;

	public AdminController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/information-admin")
	public String getInformationAdmin(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "admin/ca-nhan/InformationAdmin";
	}
	
	@GetMapping("/edit-information-admin")
	public String getEditInformationAdmin(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);

		return "admin/ca-nhan/EditInformationAdmin";
	}

	@PostMapping("/edit-information-admin")
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
		return "redirect:/admin/information-admin";
	}

	@GetMapping("/change-password")
	public String getChangePassword(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		System.out.println("chang pass (user): " + username);
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);

		return "admin/ca-nhan/ChangePassword";
	}
	
	@PostMapping("/change-password")
	public String postChangePassword(@Valid @ModelAttribute("userRequest") Account userRequest ,
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
				System.out.println("update password ");
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
		return "redirect:/admin/information-admin";
	}
	
	
}
