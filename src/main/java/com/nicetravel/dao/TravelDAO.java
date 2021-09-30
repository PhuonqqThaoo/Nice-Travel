package com.nicetravel.dao;

import com.nicetravel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDAO extends JpaRepository<Travel, Integer> {
}
