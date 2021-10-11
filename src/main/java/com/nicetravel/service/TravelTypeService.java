package com.nicetravel.service;


import com.nicetravel.entity.TravelType;

import java.util.List;

public interface TravelTypeService {
    List<TravelType> getAllTravelType();

    TravelType findById(Integer id);

    TravelType createTravelType(TravelType travelType);

    TravelType updateTravelType(TravelType travelType);

    void deleteTravelType(Integer id);
}
