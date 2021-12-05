package com.nicetravel.service;


import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelTypes;
import org.springframework.data.domain.Page;


import java.util.List;

public interface TravelTypeService {
    List<TravelTypes> getAllTravelType();

    TravelTypes findById(Integer id);

    TravelTypes createTravelType(TravelTypes travelTypes);

    TravelTypes updateTravelType(TravelTypes travelTypes);

    void deleteTravelType(Integer id);
    
    List<TravelTypes> findAll();

    List<TravelTypes> findAllAdmin();

    Page<TravelTypes> findAllAdminPage(int page, int size);

    void updateTravelTypeAdmin(TravelTypes travelTypes) throws Exception;

    void deleteTravelTypeAdmin(Integer id) throws Exception;

    TravelTypes saveTravelType(TravelTypes travelTypeRequest);

}
