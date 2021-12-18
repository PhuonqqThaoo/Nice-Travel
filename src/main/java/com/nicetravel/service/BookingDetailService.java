package com.nicetravel.service;


import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingDetailService {
    Page<BookingDetail> getAllBookingDetail(int page, int size);

    BookingDetail findById(Integer id);
    
    BookingDetail findByIdBooking(Integer id);

    BookingDetail createBookingDetail(BookingDetail bookingDetail);

    BookingDetail updateBookingDetail(BookingDetail bookingDetail);

    void deleteBookingDetail(Integer id);
    
}
