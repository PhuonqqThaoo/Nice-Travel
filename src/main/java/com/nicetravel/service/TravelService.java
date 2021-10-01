package com.nicetravel.service;

import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelDetail;

import java.util.List;

public interface TravelService {
    List<Travel> getAllTravel();

    Travel findById(Integer id);

    Travel createTravel(Travel travel);

    Travel updateTravel(Travel travel);

    void deleteTravel(Integer id);
}
