package com.nicetravel.repository;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	  
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price between ?4 and ?5" )
	  Page<Travel> searchTour2(String depart, String desti, String sd, BigDecimal pmin, BigDecimal pmax, Pageable pageable) ;
	  
	  //Ko ngày, komin, komax
	 // @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2%" )
	 // List<Travel> searchTourNotDate(String depart, String desti) ;
	  
	  //ko ngày, có min có max
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price between ?3 and ?4" )
	  List<Travel> searchTourMinMaxNoDate(String depart, String desti,BigDecimal pmin, BigDecimal pmax);
	  
	  @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price between ?3 and ?4" )
	  Page<Travel> searchTourMinMaxNoDate2(String depart, String desti,BigDecimal pmin, BigDecimal pmax, Pageable pageable);
	  
	  // ko ngày ko max, có min
	 // @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price >= ?3" )
	  //List<Travel> searchTourMinNoMaxNoDate(String depart, String desti,BigDecimal pmin);
	  
	  //ko ngày , ko min, có max
	  //@Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and price <= ?3" )
	  //List<Travel> searchTourMaxNoMinNoDate(String depart, String desti,BigDecimal pmax);
	  
	  // có ngày , có min ko max
	  //@Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price >= ?4" )
	  //List<Travel> searchTourNoMax(String depart, String desti, String sd, BigDecimal pmin) ;
	  
	  //có ngày ko min có max
	 // @Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103) and price <= ?4" )
	  //List<Travel> searchTourNoMin(String depart, String desti, String sd, BigDecimal pmax) ;
	  
	  //có ngày ko max ko min
	  //@Query("SELECT t FROM Travel t WHERE t.place LIKE %?1% and t.name LIKE %?2% and t.startDate = CONVERT(DATETIME, ?3 ,103)" )
	 // List<Travel> searchTourNoMinNoMax(String depart, String desti, String sd) ;
	  
	/*
	 * @Query("SELECT t FROM Travel t WHERE t.typeId.id=?1") List<Travel>
	 * findByTypeId(Integer tid,Pageable pageable);
	 */
	  
	  
	    @Query("SELECT t FROM Travel t WHERE t.typeId.id=?1")
		Page<Travel> findByTypeId(Integer tid,Pageable pageable);
    
//    // số lượng bán ra 
//    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
//	List<TotalSold> getTotalSold();
    
    @Query(value = "{CALL sp_getTotalSold()}", nativeQuery = true)
   	List<String[][]> getTotalSold();
   	
   	@Query("SELECT new com.nicetravel.entity.Total(u.name, (100- ((convert(float,u.quantityNew) / convert(float,u.quantity)) * 100)),u.quantity, (u.quantity- u.quantityNew) ) From Travel u ")
   	List<Total> getTotal();
   	
   	@Query(value ="SELECT * FROM Travel WHERE is_deleted = 0 ", nativeQuery = true)
    List<Travel> findAllByTravel();
   	
//   	@Query("SELECT u FROM Travel u WHERE u.id =?1")
//   	Travel findTravelById(Integer id);
   	@Modifying(clearAutomatically =true)
    @Query(value="UPDATE Travel SET name = ?1, departure_place = ?2,place = ?3, price = ?4, start_date= ?5, end_date = ?6, quantity= ?7, quantity_new = ?8, hour = ?9, slug = ?10 WHERE id = ?11", nativeQuery = true)
    void updateTravelAdmin(String name, String departurePlace,String place, BigDecimal price,Date start_date, Date end_date, Integer quantity , Integer quantityNew, Integer hour, String slug ,Integer id);
    
   	@Modifying(clearAutomatically =true)
    @Query(value="UPDATE Travel SET  is_Deleted = 1 WHERE id =?1", nativeQuery = true)
    void deletedTravel(Integer id);
   	
   	

}
