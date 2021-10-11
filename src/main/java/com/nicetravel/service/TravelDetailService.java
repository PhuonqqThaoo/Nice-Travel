package com.nicetravel.service;


import com.nicetravel.entity.TravelDetail;

import java.util.List;

public interface TravelDetailService {
    List<TravelDetail> getAllTravelDetail();

    TravelDetail findById(Integer id);

    TravelDetail createTravelDetail(TravelDetail travelDetail);

    TravelDetail updateTravelDetail(TravelDetail travelDetail);

    void deleteTravelDetail(Integer id);
}
