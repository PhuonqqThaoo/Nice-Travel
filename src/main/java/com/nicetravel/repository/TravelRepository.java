package com.nicetravel.repository;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;

import java.math.BigDecimal;
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
    
	
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price between ?4 and ?5" )
	  List<Travel> searchTour(String depart, String desti, String sd, BigDecimal pmin, BigDecimal pmax) ;
	  
	  //Ko ngày, komin, komax
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2%" )
	  List<Travel> searchTourNotDate(String depart, String desti) ;
	  
	  //ko ngày, có min có max
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price between ?3 and ?4" )
	  List<Travel> searchTourMinMaxNoDate(String depart, String desti,BigDecimal pmin, BigDecimal pmax);
	  
	  // ko ngày ko max, có min
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price >= ?3" )
	  List<Travel> searchTourMinNoMaxNoDate(String depart, String desti,BigDecimal pmin);
	  
	  //ko ngày , ko min, có max
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price <= ?3" )
	  List<Travel> searchTourMaxNoMinNoDate(String depart, String desti,BigDecimal pmax);
	  
	  // có ngày , có min ko max
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price >= ?4" )
	  List<Travel> searchTourNoMax(String depart, String desti, String sd, BigDecimal pmin) ;
	  
	  //có ngày ko min có max
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price <= ?4" )
	  List<Travel> searchTourNoMin(String depart, String desti, String sd, BigDecimal pmax) ;
	  
	  //có ngày ko max ko min
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103)" )
	  List<Travel> searchTourNoMinNoMax(String depart, String desti, String sd) ;
	  
	  
    @Query("SELECT t FROM Travel t WHERE t.typeId.id=?1")
	List<Travel> findByTypeId(Integer tid);
    
//    // số lượng bán ra 
//    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
//	List<TotalSold> getTotalSold();
    
    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
   	List<String[][]> getTotalSold();
   	
   	@Query("SELECT new com.nicetravel.entity.Total(u.name, (100- ((convert(float,u.quantityNew) / convert(float,u.quantity)) * 100)),u.quantity, (u.quantity- u.quantityNew) ) From Travel u ")
   	List<Total> getTotal();
}
