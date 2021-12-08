package com.nicetravel.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.BookingDetail;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayController {
	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private BookingDetailService bookingDetailService;
	
	@Autowired
	private PaymentService paymentService;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/thanhtoan/{id}")
	public String index(@PathVariable("id") Integer id,Model model){
		BookingDetail detail = bookingDetailService.findByIdBooking(id);
		model.addAttribute("item", detail);
		return "pay/index";
	}
	
	@PostMapping("/pay")
	public String pay(HttpServletRequest request,@RequestParam("id") String id){
		String cancelUrl = "http://localhost:8081/" + URL_PAYPAL_CANCEL;
		String successUrl = "http://localhost:8081/" + URL_PAYPAL_SUCCESS;
		Double price = Double.parseDouble(request.getParameter("price"));
		try {
			Payment payment = paymentService.createPayment(
					price,
					"USD",
					"paypal",
					"sale",
					id,
					cancelUrl,
					successUrl);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(){
		return "paypal/cancel";
	}
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paymentService.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			//getTransactions().get(0).getAmount().getTotal()
			if(payment.getState().equals("approved")){
				return "paypal/success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
}
