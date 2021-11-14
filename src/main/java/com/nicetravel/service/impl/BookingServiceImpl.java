package com.nicetravel.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.repository.BookingDetailRepository;
import com.nicetravel.repository.BookingRepository;
import com.nicetravel.service.BookingService;

import org.hibernate.type.descriptor.java.BigDecimalTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	BookingRepository bookingRepository;

	@Autowired
	BookingDetailRepository bookingDetailRepository;

	@Autowired
	public BookingServiceImpl(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	public List<Booking> getAllBooking() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking findById(Integer id) {
		return bookingRepository.findById(id).get();
	}

	@Override
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public Booking updateBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public void deleteBooking(Integer id) {
		bookingRepository.deleteById(id);
	}

	@Override
	public Integer getBookingInDay() {
		return bookingRepository.getOrderInDay();
	}

	@Override
	public Double getRevenueInDay() {
		return bookingRepository.getRevenueInDay();
	}

	@Override
	public Double getRevenue() {
		return bookingRepository.getRevenue();
	}

	@Override
	public Booking createBookingJson(JsonNode bookingData) {
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = mapper.convertValue(bookingData, Booking.class);
		bookingRepository.save(booking);

		BookingDetail bookingDetail = mapper.convertValue(bookingData.get("bookingDetails"), BookingDetail.class);
		bookingDetail.setBookingId(booking);
		bookingDetailRepository.save(bookingDetail);

		return booking;
	}
}
