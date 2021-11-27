package com.nicetravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nicetravel.entity.Booking;
@Repository
public interface StatsRepository extends JpaRepository<Booking, Integer>{
	//Stats:Viết tắt từ thống kê
	
	@Query(value = "{CALL sp_getTotalPriceOneMonth(:month, :year)}", nativeQuery = true)
	String getTotalPriceOneMonth(@Param("month") String month, 
			@Param("year") String year);
	
	@Query(value = "{CALL sp_getTotalPriceFromTo(:from, :to)}", nativeQuery = true)
	String[][] getTotalPriceFromTo(@Param("from") String month, 
			@Param("to") String year);

}
