package com.nicetravel.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/admin/thong-tin-khach-hang") 
public class UserController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		model.addAttribute("userRequest",new Account());
		return "/admin/khach-hang/ThongTinKhachHang";
	}
	
	// localhost:8081/admin/thong-tin-khach-hang/edit?username={username}
	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("username") String username, Model model) {
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/admin/khach-hang/ThongTinKhachHang::#form";
	}
	
	@PostMapping("/edit")
	public String doPostEdit(@Valid @ModelAttribute("userRequest") Account userRequest ,
							BindingResult result,
							 RedirectAttributes redirect) {
		String errorMessage = null;
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
		return "redirect:/admin/thong-tin-khach-hang";
	}
	@GetMapping("/delete")
	public String doGetDeleted(@RequestParam(name="username",required = true)String username,
			RedirectAttributes redirect) {
		try {
			accountService.delete(username);
			String successMessage = "User " +username + " was deleted!";
			redirect.addFlashAttribute("successMessage", successMessage);
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("errorMessage ", "Cannot delete user, please try again!");
		}
		return "redirect:/admin/thong-tin-khach-hang";
	}
	
	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("userRequest") Account userRequest,
			BindingResult result,
			RedirectAttributes redirect) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage ="User is not valid";
			}else {
				accountService.save(userRequest);
				String successMessage = "User " + userRequest.getFullname() + " was created!";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot create user, please try again!";
		}
		
		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		
		return "redirect:/admin/thong-tin-khach-hang";
	}
}
