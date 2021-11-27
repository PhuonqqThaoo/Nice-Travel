package com.nicetravel.repository;

import com.nicetravel.entity.Booking;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	@Query(value = "{CALL sp_getOrderInDay()}", nativeQuery = true)
	Integer getOrderInDay();

	@Query(value = "{CALL sp_getRevenueInDay()}", nativeQuery = true)
	Double getRevenueInDay();

	@Query(value = "{CALL sp_getRevenue()}", nativeQuery = true)
	Double getRevenue();

	@Query(value = "{CALL sp_getLastRevenue()}", nativeQuery = true)
	Double getLastRevenue();
	
	@Query(value = "SELECT created_date, SUM(o2.price) "
			+ "					FROM Booking o1 inner join Booking_detail o2 on o1.id = o2.booking_Id "
			+ "					WHERE o1.created_date between ?1 and ?2 "
			+ "					group by created_date",nativeQuery = true)
	String[][] getTotalPriceFromTo(String from, String to);

	// Fix sau
	@Query("SELECT u FROM Booking u WHERE u.accountId = ?1")
	List<Booking> getAllBookingByAcId(Account id);
}
