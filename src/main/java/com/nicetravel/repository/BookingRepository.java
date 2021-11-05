package com.nicetravel.repository;

import com.nicetravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
//	@Query("SELECT count(u.id) from booking u ")
//	Integer getBookingInDay();
}
