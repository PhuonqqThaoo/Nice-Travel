package com.nicetravel.service;

import com.nicetravel.entity.Booking;


import java.util.List;

public interface BookingService {
    List<Booking> getAllBooking();

    Booking findById(Integer id);

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking booking);

    void deleteBooking(Integer id);
}
