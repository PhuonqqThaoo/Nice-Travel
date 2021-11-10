package com.nicetravel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nicetravel.entity.Booking;


import java.util.List;

public interface BookingService {
    List<Booking> getAllBooking();

    Booking findById(Integer id);

    Booking createBooking(Booking booking);
    
    Booking createBookingJson(JsonNode bookingData);

    Booking updateBooking(Booking booking);

    void deleteBooking(Integer id);
    
    Integer getBookingInDay();
    
    Double getRevenueInDay();
    
    Double getRevenue();
}
