package com.nicetravel.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class ManageTourCustomerController {
	
	@GetMapping("/tour-da-dat")
	public String getManageTour() {
		return "customer/TourDaDat";
	}
}
