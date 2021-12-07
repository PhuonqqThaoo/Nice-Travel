package com.nicetravel.service.impl;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;
import com.nicetravel.repository.TravelRepository;

import com.nicetravel.service.TravelService;
import com.nicetravel.util.GenerateSlug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.management.ObjectName;
import javax.transaction.Transactional;
//import org.springframework.transaction.annotation.Transactional;

@Service
public class TravelServiceImpl implements TravelService {

    TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getAllTravel() {
        return travelRepository.getAll();
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

	/*
	 * @Override public List<Travel> findByTypeId(Integer tid) { return
	 * travelRepository.findByTypeId(tid); }
	 */

	@Override
	public Integer getCountTravel() {
		return travelRepository.getcountTravel();
	}

	@Override
	public List<Total> getTotal() {
		return travelRepository.getTotal();
	}

	/*
	 * @Override public List<Travel> searchTour(String depart, String desti, String
	 * sd,BigDecimal pmin, BigDecimal pmax) { // TODO Auto-generated method stub
	 * if(ObjectUtils.isEmpty(sd) && ObjectUtils.isEmpty(pmin) &&
	 * ObjectUtils.isEmpty(pmax)) { System.out.println("1"); return
	 * travelRepository.searchTourNotDate(depart,desti);
	 * 
	 * } else if(ObjectUtils.isEmpty(sd)){ System.out.println("2"); return
	 * travelRepository.searchTourMinMaxNoDate(depart, desti, pmin, pmax);
	 * 
	 * }else if(ObjectUtils.isEmpty(sd) && ObjectUtils.isEmpty(pmax)) {
	 * System.out.println("3"); return
	 * travelRepository.searchTourMinNoMaxNoDate(depart, desti, pmin);
	 * 
	 * }else if(ObjectUtils.isEmpty(sd) && ObjectUtils.isEmpty(pmin)) {
	 * System.out.println("4"); return
	 * travelRepository.searchTourMaxNoMinNoDate(depart, desti, pmax); }
	 * 
	 * else if(ObjectUtils.isEmpty(pmax)) { System.out.println("5"); return
	 * travelRepository.searchTourNoMax(depart, desti, sd, pmin); }
	 * 
	 * else if(ObjectUtils.isEmpty(pmin)) { System.out.println("6"); return
	 * travelRepository.searchTourNoMin(depart, desti, sd, pmax); } else
	 * if(ObjectUtils.isEmpty(pmin) && ObjectUtils.isEmpty(pmax)) {
	 * System.out.println("7"); return travelRepository.searchTourNoMinNoMax(depart,
	 * desti, sd); } else { return travelRepository.searchTour(depart,
	 * desti,sd,pmin,pmax); } }
	 */

	@Override
	public Page<Travel> searchTour2(String depart, String desti, String sd, BigDecimal pmin, BigDecimal pmax,
			Pageable pageable) {
		if(ObjectUtils.isEmpty(sd)){
			System.out.println("2");
			return travelRepository.searchTourMinMaxNoDate2(depart, desti, pmin, pmax,pageable);
		}
		else {
			return travelRepository.searchTour2(depart, desti,sd,pmin,pmax,pageable);
		}
	}
	
	@Override
	public Page<Travel> search(String depart,String desti, String sd,BigDecimal pmin, BigDecimal pmax,int pageNumber){
			Pageable pageable = PageRequest.of(pageNumber - 1, 6);
			
			if(ObjectUtils.isEmpty(sd)) {
				return travelRepository.searchTourMinMaxNoDate2(depart, desti, pmin, pmax, pageable);
			}
			return travelRepository.searchTour2(depart, desti, sd, pmin, pmax, pageable);
	}
	
	@Override
	public Page<Travel> findByTypeId(Integer tid, Pageable pageable) {
		return travelRepository.findByTypeId(tid,pageable);
	}

	@Override

	public Page<Travel> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return travelRepository.getAll(pageable);
	}

	@Override
	public List<Travel> getFavoriteTour() {
		return travelRepository.getTourFavorite();
	}

	@Override
	public Page<Travel> getTravelInMonth(int page, int size) {
		return travelRepository.getTravelInMonth(PageRequest.of(page, size));
	}

	@Override
	public Page<Travel> getFindAllByTravelActive(int page, int size) {

		return travelRepository.findAllByTravelActive(PageRequest.of(page, size));
	}

	@Override
	public Page<Travel> getFindAllByTravel(int page, int size) {
		return travelRepository.findAllByTravel(PageRequest.of(page, size));
	}

	@Override
	public Page<Travel> getFindAllByTravelExpires(int page, int size) {
		return  travelRepository.findAllByTravelExpires(PageRequest.of(page, size));
	}

	@Override
	public Page<Travel> getFindAllByTravelNonActive(int page, int size) {
		return  travelRepository.findAllByTravelNonActive(PageRequest.of(page, size));
	}

//	@Override
//	public List<String[][]> getTotalSold() {
//		// TODO Auto-generated method stub
//		return travelRepository.getTotalSold();
//	}

//	public List<Travel> getFindAllByTravel() {
//		return travelRepository.findAllByTravel();
//	}

	@Override
	@Transactional 
	public void updateTraveladmin(Travel travel) throws Exception{
		if (ObjectUtils.isEmpty(travel)) {
			throw new Exception("Travel cannot be empty");
		}
			travel.setQuantity(travel.getQuantityNew());
			String slug = GenerateSlug.toSlug(travel.getName());
			travel.setSlug(slug);
			travelRepository.updateTravelAdmin(travel.getName(), travel.getDeparturePlace(),travel.getPlace(),travel.getPrice(),travel.getStartDate(),travel.getEndDate(), travel.getQuantity(),travel.getQuantityNew(),travel.getHour(),travel.getSlug(),travel.getId());
		
	}

	@Override
	@Transactional
	public void sp_updateEXD2() {
		 travelRepository.sp_updateEXD2();
	}

	@Override
	@Transactional
	public void deleteTravelAdmin(Integer id) throws Exception {
		if (ObjectUtils.isEmpty(id)) {
			throw new Exception("Travel cannot be empty");
		}
		travelRepository.deletedTravel(id);
		
	}

	@Override
	public Travel saveTravel(Travel travelRequest) {
		travelRequest.setQuantity(travelRequest.getQuantityNew());
		String slug = GenerateSlug.toSlug(travelRequest.getName());
		travelRequest.setSlug(slug);
		return travelRepository.saveAndFlush(travelRequest);
	}

	@Override
	public Integer countDaLatTour() {
		// TODO Auto-generated method stub
		return travelRepository.countDaLatTour();
	}

	@Override
	public Integer countDaNangTour() {
		// TODO Auto-generated method stub
		return travelRepository.countDaNangTour();
	}

	@Override
	public Integer countPhuQuocTour() {
		// TODO Auto-generated method stub
		return travelRepository.countPhuQuocTour();
	}

	@Override
	public Integer countHaNoiTour() {
		// TODO Auto-generated method stub
		return travelRepository.countHaNoiTour();
	}

	@Override
	@Transactional
	public void updateEX() {
		// TODO Auto-generated method stub
		  travelRepository.updateEX();
	}






	

	
	
}
