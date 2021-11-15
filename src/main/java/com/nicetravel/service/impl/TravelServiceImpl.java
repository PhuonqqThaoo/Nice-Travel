package com.nicetravel.service.impl;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;
import com.nicetravel.repository.TravelRepository;

import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.management.ObjectName;

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
	public List<Travel> searchTour(String depart, String desti, String sd,BigDecimal pmin, BigDecimal pmax) {
		// TODO Auto-generated method stub
		if(ObjectUtils.isEmpty(sd) && ObjectUtils.isEmpty(pmin) && ObjectUtils.isEmpty(pmax)) {
			System.out.println("1");
			return travelRepository.searchTourNotDate(depart,desti);
			
		}
		else if(ObjectUtils.isEmpty(sd)){
			System.out.println("2");
			return travelRepository.searchTourMinMaxNoDate(depart, desti, pmin, pmax);
		
		}else if(ObjectUtils.isEmpty(sd)  &&  ObjectUtils.isEmpty(pmax)) {
			System.out.println("3");
			return travelRepository.searchTourMinNoMaxNoDate(depart, desti, pmin);
		
		}else if(ObjectUtils.isEmpty(sd)  && ObjectUtils.isEmpty(pmin)) {
			System.out.println("4");
			return travelRepository.searchTourMaxNoMinNoDate(depart, desti, pmax);
		}
		
		else if(ObjectUtils.isEmpty(pmax)) {
			System.out.println("5");
			return travelRepository.searchTourNoMax(depart, desti, sd, pmin);
		}
		
		else if(ObjectUtils.isEmpty(pmin)) {
			System.out.println("6");
			return travelRepository.searchTourNoMin(depart, desti, sd, pmax);
		}	
		else if(ObjectUtils.isEmpty(pmin) && ObjectUtils.isEmpty(pmax)) {
			System.out.println("7");
			return travelRepository.searchTourNoMinNoMax(depart, desti, sd);
		}
		else {
			return travelRepository.searchTour(depart, desti,sd,pmin,pmax);
		}
	}

//	@Override
//	public List<String[][]> getTotalSold() {
//		// TODO Auto-generated method stub
//		return travelRepository.getTotalSold();
//	}

	

	
	
}
