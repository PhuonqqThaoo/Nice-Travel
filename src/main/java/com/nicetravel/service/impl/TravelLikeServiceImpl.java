package com.nicetravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.repository.TravelLikeRepository;
import com.nicetravel.service.TravelLikeService;

@Service
public class TravelLikeServiceImpl implements TravelLikeService {
	
	TravelLikeRepository travelLikeRepository;

	@Autowired
	public TravelLikeServiceImpl(TravelLikeRepository travelLikeRepository) {
		this.travelLikeRepository = travelLikeRepository;
	}
	
	@Override
	public TravelLike createTravelLike(TravelLike travelLike) {
		return travelLikeRepository.save(travelLike);
	}

	@Override
	public void deleteTravelLike(Integer id) {
		travelLikeRepository.deleteById(id);
	}

	@Override
	public List<TravelLike> getAllTravelLikeByIdAcount(Account accountId) {
		return travelLikeRepository.getAllTravelLikeByIdAcount(accountId);
	}
}
