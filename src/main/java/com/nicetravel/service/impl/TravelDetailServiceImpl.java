package com.nicetravel.service.impl;

import com.nicetravel.entity.TravelDetail;
import com.nicetravel.repository.TravelDetailRepository;
import com.nicetravel.service.TravelDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelDetailServiceImpl implements TravelDetailService {

    private final TravelDetailRepository travelDetailRepository;

    public TravelDetailServiceImpl(TravelDetailRepository travelDetailRepository) {
        this.travelDetailRepository = travelDetailRepository;
    }


    @Override
    public List<TravelDetail> getAllTravelDetail() {
        return travelDetailRepository.findAll();
    }

    @Override
    public TravelDetail findById(Integer id) {
        return travelDetailRepository.findById(id).get();
    }

    @Override
    public TravelDetail createTravelDetail(TravelDetail travelDetail) {
        return travelDetailRepository.save(travelDetail);
    }

    @Override
    public TravelDetail updateTravelDetail(TravelDetail travelDetail) {
        return travelDetailRepository.save(travelDetail);
    }

    @Override
    public void deleteTravelDetail(Integer id) {
        travelDetailRepository.deleteById(id);
    }
}
