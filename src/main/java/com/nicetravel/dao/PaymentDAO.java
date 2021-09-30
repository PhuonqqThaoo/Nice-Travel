package com.nicetravel.dao;

import com.nicetravel.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDAO extends JpaRepository<Payment, Integer> {
}
