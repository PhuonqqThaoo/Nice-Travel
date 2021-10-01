package com.nicetravel.repository;

import com.nicetravel.entity.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeTypeRepository extends JpaRepository<AgeType, Integer> {
}
