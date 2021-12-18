package com.nicetravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nicetravel.entity.BookingDetail;
import com.nicetravel.repository.BookingDetailRepository;
import com.nicetravel.service.BookingDetailService;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    BookingDetailRepository bookingDetailRepository;

    @Autowired
    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository) {
        this.bookingDetailRepository = bookingDetailRepository;
    }

  

    @Override
    public BookingDetail findById(Integer id) {
        return bookingDetailRepository.findById(id).get();
    }

    @Override
    public BookingDetail createBookingDetail(BookingDetail bookingDetail) {
        return bookingDetailRepository.save(bookingDetail);
    }

    @Override
    public BookingDetail updateBookingDetail(BookingDetail bookingDetail) {
        return bookingDetailRepository.save(bookingDetail);
    }

    @Override
    public void deleteBookingDetail(Integer id) {
        bookingDetailRepository.deleteById(id);
    }

	@Override
	public BookingDetail findByIdBooking(Integer id) {
		return bookingDetailRepository.findByIdBooking(id);
	}



	@Override
	public Page<BookingDetail> getAllBookingDetail(int page, int size) {
		// TODO Auto-generated method stub
		return bookingDetailRepository.findAll(PageRequest.of(page, size));
	}






}
