package com.nicetravel.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.nicetravel.entity.Booking;
import com.nicetravel.service.BookingService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/booking")
public class BookingRestController {
	@Autowired BookingService bookingService;
	
	@GetMapping
	public List<Booking> getAllBooking(){
		return bookingService.getAllBooking();
	}
	
	@PostMapping
	public Booking create(@RequestBody JsonNode bookingData) {
		return bookingService.createBookingJson(bookingData);
	}
}