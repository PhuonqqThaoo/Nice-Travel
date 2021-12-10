package com.nicetravel.controller;

import java.util.Date;

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

import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
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
	
	@GetMapping("/detail-booking/{id}")
	public String index(@PathVariable("id") Integer id,Model model){
		BookingDetail detail = bookingDetailService.findByIdBooking(id);
		model.addAttribute("item", detail);
		return "pay/index";
	}
	
	@PostMapping("/thanh-toan")
	public String pay(HttpServletRequest request,@RequestParam("idBooking") String idBooking, @RequestParam("price") Double price){
		String cancelUrl = "http://localhost:8081/" + URL_PAYPAL_CANCEL;
		String successUrl = "http://localhost:8081/" + URL_PAYPAL_SUCCESS;
		try {
			Payment payment = paymentService.createPayment(
					price,
					"USD",
					"paypal",
					"sale",
					idBooking,
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
		return "pay/cancel";
	}
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paymentService.executePayment(paymentId, payerId);
			System.out.println("id booking " + payment.getTransactions().get(0).getDescription());
			System.out.println("price " + payment.getTransactions().get(0).getAmount().getTotal());
			//upadate booking
			Booking booking = bookingService.findById(Integer.parseInt(payment.getTransactions().get(0).getDescription()));
			booking.setPayBoolean(true);
			bookingService.updateBooking(booking);
			//thiếu lưu vào payment
			com.nicetravel.entity.Payment pay = new  com.nicetravel.entity.Payment();
			pay.setBookingId(booking);
			pay.setPayTime(new Date());
			pay.setTotalPrice(booking.getTotalPrice());
			paymentService.createPayment(pay);
			if(payment.getState().equals("approved")){
				return "pay/success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
}
