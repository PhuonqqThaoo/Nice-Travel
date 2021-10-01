package com.nicetravel.service.impl;

import com.nicetravel.entity.PriceDetail;
import com.nicetravel.repository.PriceDetailRepository;
import com.nicetravel.service.PriceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceDetailServiceImpl implements PriceDetailService {

    PriceDetailRepository priceDetailRepository;

    @Autowired
    public PriceDetailServiceImpl(PriceDetailRepository priceDetailRepository) {
        this.priceDetailRepository = priceDetailRepository;
    }

    @Override
    public List<PriceDetail> getAllPriceDetail() {
        return priceDetailRepository.findAll();
    }

    @Override
    public PriceDetail findById(Integer id) {
        return priceDetailRepository.findById(id).get();
    }

    @Override
    public PriceDetail createPriceDetail(PriceDetail priceDetail) {
        return priceDetailRepository.save(priceDetail);
    }

    @Override
    public PriceDetail updatePriceDetail(PriceDetail priceDetail) {
        return priceDetailRepository.save(priceDetail);
    }

    @Override
    public void deletePriceDetail(Integer id) {
        priceDetailRepository.deleteById(id);
    }
}
