package com.nicetravel.service;


import com.nicetravel.entity.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayment();

    Payment findById(Integer id);

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment payment);

    void deletePayment(Integer id);
    
    com.paypal.api.payments.Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
    
	com.paypal.api.payments.Payment createPayment(
			Double total,
			String currency,
			String method,
			String intent,
			String descriptionIdBooking,
			String cancelUrl,
			String successUrl) throws PayPalRESTException;
}
