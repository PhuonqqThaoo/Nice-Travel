package com.nicetravel.service.impl;

import com.nicetravel.entity.TravelType;
import com.nicetravel.repository.TravelTypeRepository;
import com.nicetravel.service.TravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelTypeServiceImpl implements TravelTypeService {

    TravelTypeRepository travelTypeRepository;

    @Autowired
    public TravelTypeServiceImpl(TravelTypeRepository travelTypeRepository) {
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public List<TravelType> getAllTravelType() {
        return travelTypeRepository.findAll();
    }

    @Override
    public TravelType findById(Integer id) {
        return travelTypeRepository.findById(id).get();
    }

    @Override
    public TravelType createTravelType(TravelType travelType) {
        return travelTypeRepository.save(travelType);
    }

    @Override
    public TravelType updateTravelType(TravelType travelType) {
        return travelTypeRepository.save(travelType);
    }

    @Override
    public void deleteTravelType(Integer id) {
        travelTypeRepository.deleteById(id);
    }
}
