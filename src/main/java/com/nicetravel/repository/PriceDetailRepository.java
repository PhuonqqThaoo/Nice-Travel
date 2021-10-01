package com.nicetravel.repository;

import com.nicetravel.entity.PriceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDetailRepository extends JpaRepository<PriceDetail, Integer> {
}
