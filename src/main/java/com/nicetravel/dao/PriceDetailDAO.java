package com.nicetravel.dao;

import com.nicetravel.entity.PriceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceDetailDAO extends JpaRepository<PriceDetail, Integer> {
}
