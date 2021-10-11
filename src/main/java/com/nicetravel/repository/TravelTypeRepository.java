package com.nicetravel.repository;

import com.nicetravel.entity.TravelTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTypeRepository extends JpaRepository<TravelTypes, Integer> {
}
