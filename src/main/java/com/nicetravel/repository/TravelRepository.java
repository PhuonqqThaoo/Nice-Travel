package com.nicetravel.repository;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.TotalSold;
import com.nicetravel.entity.Travel;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {
	
	@Query("SELECT count(u.id) FROM Travel u ")
	Integer getcountTravel();

    @Query("SELECT u FROM Travel u WHERE u.slug LIKE %?1%")
    Travel findTravelBySlug(String slug); 
    
	
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103)" )
	  List<Travel> searchTour(String depart, String desti, String sd) ;
	  
    @Query("SELECT t FROM Travel t WHERE t.typeId.id=?1")
	List<Travel> findByTypeId(Integer tid);
    
//    // số lượng bán ra 
//    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
//	List<TotalSold> getTotalSold();
    
    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
   	List<String[][]> getTotalSold();
   	
   	@Query("SELECT new com.nicetravel.entity.Total(u.name, (100- ((convert(float,u.quantityNew) / convert(float,u.quantity)) * 100)) ) From Travel u ")
   	List<Total> getTotal();
}
