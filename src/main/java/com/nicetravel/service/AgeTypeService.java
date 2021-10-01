package com.nicetravel.service;

import com.nicetravel.entity.AccountDetail;
import com.nicetravel.entity.AgeType;

import java.util.List;

public interface AgeTypeService {
    List<AgeType> getAllAgeType();

    AgeType findById(Integer id);

    AgeType createAgeType(AgeType ageType);

    AgeType updateAgeType(AgeType ageType);

    void deleteAgeType(Integer id);
}
