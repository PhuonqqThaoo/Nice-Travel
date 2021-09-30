package com.nicetravel.dao;

import com.nicetravel.entity.TravelDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDetailDAO extends JpaRepository<TravelDetail, Integer> {
}
