package com.nicetravel.service.impl;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.TotalSold;
import com.nicetravel.entity.Travel;
import com.nicetravel.repository.TravelRepository;

import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getAllTravel() {
        return travelRepository.findAll();
    }

    @Override
    public Travel findTravelById(Integer id) {
        return travelRepository.findById(id).get();
    }

    @Override
    public Travel createTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public Travel updateTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public void deleteTravel(Integer id) {
        travelRepository.deleteById(id);
    }

    @Override
    public Travel findTravelBySlug(String slug) {
        return travelRepository.findTravelBySlug(slug);
    }

	@Override
	public List<Travel> findByTypeId(Integer tid) {
		return travelRepository.findByTypeId(tid);
	}

	@Override
	public Integer getCountTravel() {
		return travelRepository.getcountTravel();
	}

	@Override
	public List<Total> getTotal() {
		return travelRepository.getTotal();
	}

	@Override
	public List<Travel> searchTour(String depart, String desti, String sd) {
		// TODO Auto-generated method stub
		return travelRepository.searchTour(depart, desti,sd);
	}

//	@Override
//	public List<String[][]> getTotalSold() {
//		// TODO Auto-generated method stub
//		return travelRepository.getTotalSold();
//	}

	

	
	
}
