package com.nicetravel.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/admin/thong-tin-nhan-vien")
public class StaffController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Account> list = accountService.findAllByStaff();
		model.addAttribute("listStaff",list);
		return "/admin/nhan-vien/ThongTinNhanVien";
	}

}
