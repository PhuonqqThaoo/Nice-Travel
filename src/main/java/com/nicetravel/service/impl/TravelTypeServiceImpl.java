package com.nicetravel.service.impl;

import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.repository.TravelTypeRepository;
import com.nicetravel.service.TravelTypeService;


import com.nicetravel.util.GenerateSlug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
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

	@Override
	public List<TravelTypes> findAll() {
			return travelTypeRepository.findAll();

	}

    @Override
    public List<TravelTypes> findAllAdmin() {
        return travelTypeRepository.findAllAdmin();
    }

    @Override
    public Page<TravelTypes> findAllAdminPage(int page, int size) {
        return travelTypeRepository.findAllAdminPage(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public void updateTravelTypeAdmin(TravelTypes travelTypes) throws Exception {
        if (ObjectUtils.isEmpty(travelTypes)) {
            throw new Exception("TravelType cannot be empty");
        }
        String slug = GenerateSlug.toSlug(travelTypes.getDescription());
        travelTypes.setSlug(slug);
        System.out.println(travelTypes.getSlug());
         travelTypeRepository.updateTravelTypeAdmin(travelTypes.getType(),travelTypes.getDescription(),travelTypes.getSlug(),travelTypes.getId());
    }

    @Override
    @Transactional
    public void deleteTravelTypeAdmin(Integer id) throws Exception {
        if (ObjectUtils.isEmpty(id)) {
            throw new Exception("Travel cannot be empty");
        }
        travelTypeRepository.deletedTravelType(id);
    }

    @Override
    public TravelTypes saveTravelType(TravelTypes travelTypeRequest) {
        String slug = GenerateSlug.toSlug(travelTypeRequest.getDescription());
        travelTypeRequest.setSlug(slug);
        return travelTypeRepository.saveAndFlush(travelTypeRequest);
    }


}
