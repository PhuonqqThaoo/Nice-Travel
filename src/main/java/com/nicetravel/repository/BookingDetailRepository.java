package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
	// Fix sau
	@Query("SELECT u FROM BookingDetail u WHERE u.bookingId.id = ?1")
	BookingDetail findByIdBooking(Integer id);
	
	//Tìm kiem
	@Query("SELECT u FROM BookingDetail u WHERE u.bookingId.createdDate BETWEEN CONVERT(datetime,?1) AND CONVERT(datetime,?2)")
	Page<BookingDetail> searchWithDate(String start, String end,Pageable pageable);
		
}
