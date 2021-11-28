package com.nicetravel.repository;

import com.nicetravel.entity.TravelTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelTypeRepository extends JpaRepository<TravelTypes, Integer> {
    @Query(value ="SELECT * From travel_types where is_Deleted = 0",nativeQuery = true)
    List<TravelTypes> findAllAdmin();
}
