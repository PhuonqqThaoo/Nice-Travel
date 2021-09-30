package com.nicetravel.dao;

import com.nicetravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDAO extends JpaRepository<Booking, Integer> {
}
