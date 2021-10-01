package com.nicetravel.repository;

import com.nicetravel.entity.TravelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTypeRepository extends JpaRepository<TravelType, Integer> {
}
