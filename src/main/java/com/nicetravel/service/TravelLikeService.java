package com.nicetravel.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.ListTravelLike;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;

public interface TravelLikeService {
	
	TravelLike createTravelLike(TravelLike travelLike);
	
	void deleteTravelLike(Integer id);
	
	List<TravelLike> getAllTravelLikeByIdAcount(Account account_id);
	
	Page<TravelLike> getFavotiteTour(int page, int size);
	
	Page<TravelLike> getAll(int page,int size);

	Integer countTourLike();
}
