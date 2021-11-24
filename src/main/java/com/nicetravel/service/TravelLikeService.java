package com.nicetravel.service;

import java.util.List;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelLike;

public interface TravelLikeService {
	
	TravelLike createTravelLike(TravelLike travelLike);
	
	void deleteTravelLike(Integer id);
	
	List<TravelLike> getAllTravelLikeByIdAcount(Account accountId);
}
