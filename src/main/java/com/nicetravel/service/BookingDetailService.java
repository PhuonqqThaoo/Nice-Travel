package com.nicetravel.service;


import com.nicetravel.entity.BookingDetail;

import java.util.List;

public interface BookingDetailService {
    List<BookingDetail> getAllBookingDetail();

    BookingDetail findById(Integer id);

    BookingDetail createBookingDetail(BookingDetail bookingDetail);

    BookingDetail updateBookingDetail(BookingDetail bookingDetail);

    void deleteBookingDetail(Integer id);
}
