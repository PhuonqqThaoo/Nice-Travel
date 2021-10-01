package com.nicetravel.service;

import com.nicetravel.entity.Payment;
import com.nicetravel.entity.PriceDetail;

import java.util.List;

public interface PriceDetailService {
    List<PriceDetail> getAllPriceDetail();

    PriceDetail findById(Integer id);

    PriceDetail createPriceDetail(PriceDetail priceDetail);

    PriceDetail updatePriceDetail(PriceDetail priceDetail);

    void deletePriceDetail(Integer id);
}
