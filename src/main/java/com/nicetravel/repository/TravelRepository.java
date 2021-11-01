package com.nicetravel.repository;

import com.nicetravel.entity.Travel;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {


    @Query("SELECT u FROM Travel u WHERE u.slug LIKE %?1%")
    Travel findTravelBySlug(String slug); 
    
   
    
    @Query("SELECT t FROM Travel t WHERE t.typeId.id=?1")
	List<Travel> findByTypeId(Integer tid);

}
