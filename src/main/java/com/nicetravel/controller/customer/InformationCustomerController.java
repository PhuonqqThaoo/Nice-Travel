package com.nicetravel.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class InformationCustomerController {
	@GetMapping("/information-customer")
	public String getInformationCustomer() {
		return "customer/InformationCustomer";
	}
	
	@GetMapping("/edit-information-customer")
	public String getEditInformationCustomer() {
		return "customer/EditInformationCustomer";
	}
	
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "customer/ChangePassword";
	}
}
