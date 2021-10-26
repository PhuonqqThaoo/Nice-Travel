package com.nicetravel.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class HomeCustomerController {
	@GetMapping("")
	public String getIndex() {
		return "customer/index";
	}
}
