package com.nicetravel.repository;

import com.nicetravel.entity.TravelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelDetailRepository extends JpaRepository<TravelDetail, Integer> {
}
