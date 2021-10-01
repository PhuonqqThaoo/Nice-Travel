package com.nicetravel.service.impl;

import com.nicetravel.entity.AgeType;
import com.nicetravel.repository.AgeTypeRepository;
import com.nicetravel.service.AgeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeTypeServiceImpl implements AgeTypeService {

    AgeTypeRepository ageTypeRepository;

    @Autowired
    public AgeTypeServiceImpl(AgeTypeRepository ageTypeRepository) {
        this.ageTypeRepository = ageTypeRepository;
    }

    @Override
    public List<AgeType> getAllAgeType() {
        return ageTypeRepository.findAll();
    }

    @Override
    public AgeType findById(Integer id) {
        return ageTypeRepository.findById(id).get();
    }

    @Override
    public AgeType createAgeType(AgeType ageType) {
        return ageTypeRepository.save(ageType);
    }

    @Override
    public AgeType updateAgeType(AgeType ageType) {
        return ageTypeRepository.save(ageType);
    }

    @Override
    public void deleteAgeType(Integer id) {
        ageTypeRepository.deleteById(id);
    }
}
