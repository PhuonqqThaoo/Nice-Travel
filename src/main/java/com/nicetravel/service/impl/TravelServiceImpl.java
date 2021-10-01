package com.nicetravel.service.impl;

import com.nicetravel.entity.Travel;
import com.nicetravel.repository.TravelRepository;
import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Travel findById(Integer id) {
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
}
