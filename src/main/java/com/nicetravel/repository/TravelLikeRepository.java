package com.nicetravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.ListTravelLike;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;

@Repository
public interface TravelLikeRepository extends JpaRepository<TravelLike, Integer> {
	@Query("SELECT u FROM TravelLike u WHERE u.travel_like_account_id = ?1")
	List<TravelLike> getAllTravelLikeByIdAcount(Account id);
	
//	@Query("SELECT u from TravelLike u")
//	List<TravelLike> getFindAllAdmin();

	@Query("SELECT  distinct u.travelId, u.travel_like_account_id, u.id FROM TravelLike u ")
	Page<TravelLike> getFavoriteTour(Pageable pageable);

}
