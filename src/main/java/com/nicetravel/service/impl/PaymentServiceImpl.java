package com.nicetravel.service.impl;

import com.nicetravel.entity.Payment;
import com.nicetravel.repository.PaymentRepository;
import com.nicetravel.service.PaymentService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;
    
    @Autowired
	private APIContext apiContext;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Integer id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

	@Override
	public com.paypal.api.payments.Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}

	@Override
	public com.paypal.api.payments.Payment createPayment(Double total, String currency, String method, String intent,
			String descriptionIdBooking, String cancelUrl, String successUrl) throws PayPalRESTException {
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format("%.2f", total));
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(descriptionIdBooking);
		
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		
		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());
		
		com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		apiContext.setMaskRequestId(true);
		return payment.create(apiContext);
	}


}
