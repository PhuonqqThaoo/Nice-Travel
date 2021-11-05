package com.nicetravel.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("/information-admin")
	public String getInformationAdmin() {
		return "admin/ca-nhan/InformationAdmin";
	}
	
	@GetMapping("/edit-information-admin")
	public String getEditInformationAdmin() {
		return "admin/ca-nhan/EditInformationAdmin";
	}
	
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "admin/ca-nhan/ChangePassword";
	}
	
	
}
