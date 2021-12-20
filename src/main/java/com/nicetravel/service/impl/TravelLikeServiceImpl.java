package com.nicetravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.ListTravelLike;
import com.nicetravel.entity.Travel;
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
	public List<TravelLike> getAllTravelLikeByIdAcount(Account account_Id) {
		return travelLikeRepository.getAllTravelLikeByIdAcount(account_Id);
	}

	@Override
	public Page<TravelLike> getFavotiteTour(int page, int size) {
		// TODO Auto-generated method stub
		return travelLikeRepository.getFavoriteTour(PageRequest.of(page, size));
	}

	@Override
	public Page<TravelLike> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return travelLikeRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Integer countTourLike() {
		// TODO Auto-generated method stub
		return travelLikeRepository.countTotalTour();
	}



}
