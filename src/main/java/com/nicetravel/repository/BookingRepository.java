package com.nicetravel.repository;

import com.nicetravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	

	@Query(value = "{CALL sp_getOrderInDay()}", nativeQuery = true)
	Integer getOrderInDay();
	
	@Query(value = "{CALL sp_getRevenueInDay()}", nativeQuery = true)
	Double getRevenueInDay();
	
	@Query(value = "{CALL sp_getRevenue()}", nativeQuery = true)
	Double getRevenue();
}
