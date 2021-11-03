package com.nicetravel.service;

import com.nicetravel.entity.TravelLike;

public interface TravelLikeService {
	
	TravelLike createTravelLike(TravelLike travelLike);
	
	void deleteTravelLike(Integer id);

}
