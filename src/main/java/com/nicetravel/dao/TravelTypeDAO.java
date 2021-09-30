package com.nicetravel.dao;

import com.nicetravel.entity.TravelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelTypeDAO extends JpaRepository<TravelType, Integer> {
}
