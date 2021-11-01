package com.nicetravel.service;

import com.nicetravel.entity.Travel;


import java.util.List;

public interface TravelService {
    List<Travel> getAllTravel();

    Travel findTravelById(Integer id);

    Travel createTravel(Travel travel);

    Travel updateTravel(Travel travel);

    void deleteTravel(Integer id);

    Travel findTravelBySlug(String slug);
    
    List<Travel> findByTypeId(Integer tid);
}
