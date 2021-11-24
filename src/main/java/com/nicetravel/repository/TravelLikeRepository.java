package com.nicetravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelLike;

@Repository
public interface TravelLikeRepository extends JpaRepository<TravelLike, Integer> {
	@Query("SELECT u FROM TravelLike u WHERE u.accountId = ?1")
	List<TravelLike> getAllTravelLikeByIdAcount(Account id);

}
