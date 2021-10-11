package com.nicetravel.service.impl;

import com.nicetravel.entity.TravelTypes;
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
    public List<TravelTypes> getAllTravelType() {
        return travelTypeRepository.findAll();
    }

    @Override
    public TravelTypes findById(Integer id) {
        return travelTypeRepository.findById(id).get();
    }

    @Override
    public TravelTypes createTravelType(TravelTypes travelTypes) {
        return travelTypeRepository.save(travelTypes);
    }

    @Override
    public TravelTypes updateTravelType(TravelTypes travelTypes) {
        return travelTypeRepository.save(travelTypes);
    }

    @Override
    public void deleteTravelType(Integer id) {
        travelTypeRepository.deleteById(id);
    }
}
