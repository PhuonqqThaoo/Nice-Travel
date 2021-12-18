package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
	// Fix sau
	@Query("SELECT u FROM BookingDetail u WHERE u.bookingId.id = ?1")
	BookingDetail findByIdBooking(Integer id);
	
	
		
}
