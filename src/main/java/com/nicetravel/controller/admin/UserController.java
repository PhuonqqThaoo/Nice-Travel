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
@RequestMapping("/admin/thong-tin-khach-hang")
public class UserController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
//		model.addAttribute("userRequest",new Account());
		return "/admin/khach-hang/ThongTinKhachHang";
	}
}
