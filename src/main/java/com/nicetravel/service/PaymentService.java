package com.nicetravel.service;


import com.nicetravel.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayment();

    Payment findById(Integer id);

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment payment);

    void deletePayment(Integer id);
}
