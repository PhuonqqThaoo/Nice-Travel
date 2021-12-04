package com.nicetravel.controller.staff;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.nicetravel.export.UserExcelExporter;
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
@RequestMapping("/staff/thong-tin-khach-hang") 
public class InformationUserController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		model.addAttribute("userRequest",new Account());
		return "/staff/khach-hang/ThongTinKhachHang";
	}
	
	// localhost:8081/admin/thong-tin-khach-hang/edit?username={username}
	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("username") String username, Model model) {
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/staff/khach-hang/ThongTinKhachHang::#form";
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
		return "redirect:/staff/thong-tin-khach-hang";
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
		return "redirect:/staff/thong-tin-khach-hang";
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
		
		return "redirect:/staff/thong-tin-khach-hang";
	}


	@GetMapping("/customers/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=customers_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Account> listUsers = accountService.findByUser();

//		List<Account> list = accountService.findAllByStaff();

		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

		excelExporter.export(response);
	}
}
