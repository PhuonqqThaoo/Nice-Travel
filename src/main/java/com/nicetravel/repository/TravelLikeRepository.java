package com.nicetravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicetravel.entity.TravelLike;

@Repository
public interface TravelLikeRepository extends JpaRepository<TravelLike, Integer> {

}
