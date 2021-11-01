package com.nicetravel.service;


import com.nicetravel.entity.TravelTypes;


import java.util.List;

public interface TravelTypeService {
    List<TravelTypes> getAllTravelType();

    TravelTypes findById(Integer id);

    TravelTypes createTravelType(TravelTypes travelTypes);

    TravelTypes updateTravelType(TravelTypes travelTypes);

    void deleteTravelType(Integer id);
    
    List<TravelTypes> findAll();
}
