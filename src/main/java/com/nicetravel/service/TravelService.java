package com.nicetravel.service;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface TravelService {
    List<Travel> getAllTravel();

    Travel findTravelById(Integer id);

    Travel createTravel(Travel travel);

    Travel updateTravel(Travel travel);

    void deleteTravel(Integer id);

    Travel findTravelBySlug(String slug);
    
    //List<Travel> findByTypeId(Integer tid);
    
    Page<Travel> findByTypeId(Integer tid,Pageable pageable);
    
	/*
	 * List<Travel> searchTour(String depart,String desti, String sd,BigDecimal
	 * pmin, BigDecimal pmax);
	 */
    
    Page<Travel> searchTour2(String depart,String desti, String sd,BigDecimal pmin, BigDecimal pmax, Pageable pageable); 
    
//    Page<Travel> searchTourMinMaxNoDate2(String depart, String desti,BigDecimal pmin, BigDecimal pmax, Pageable pageable);
    Integer getCountTravel();
    
//    List<String [][]> getTotalSold();
    Page<Travel> search(String depart,String desti, String sd,BigDecimal pmin, BigDecimal pmax, int pageNumber);
    
    List<Total> getTotal();
    
    //Lọc admin
    Page<Travel> filterStartDate(Date day, Date end, Pageable pageable);

	Page<Travel> getAll(Pageable pageable);
	
	List<Travel> getFavoriteTour();

    Page<Travel> getTravelInMonth(int page, int size);

    Page<Travel> getFindAllByTravelActive(int page, int size);

    Page<Travel> getFindAllByTravel(int page, int size);

    Page<Travel> getFindAllByTravelExpires(int page, int size);

    Page<Travel> getFindAllByTravelNonActive(int page, int size);

    
   void updateTraveladmin(Travel travel) throws Exception;

   void sp_updateEXD2() ;
   
   void deleteTravelAdmin(Integer id) throws Exception;
   
   Travel saveTravel(Travel travelRequest);

   Integer countDaLatTour();
   
   Integer countDaNangTour();
   
   Integer countPhuQuocTour();
   
   Integer countHaNoiTour();
   
   void updateEX();

    String[][] getTotalTravelFromTo(String from, String to);
}
