package com.nicetravel.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.Booking;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.entity.Travel;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.BookingDetailRepository;
import com.nicetravel.repository.BookingRepository;
import com.nicetravel.repository.StatsRepository;
import com.nicetravel.repository.TravelRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;

import org.hibernate.type.descriptor.java.BigDecimalTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

	BookingRepository bookingRepository;

	@Autowired
	private StatsRepository repo;

	@Autowired
	BookingDetailRepository bookingDetailRepository;

	@Autowired
	TravelRepository travelRepository;
	
	@Autowired
	AccountRepository accountRepository;

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
		booking.setBooking_account_id(booking.getBooking_account_id());
		booking.setCreatedDate(booking.getCreatedDate());
		booking.setAddress(booking.getAddress());
		booking.setPhone(booking.getPhone());
		booking.setTotalPrice(booking.getTotalPrice());
		booking.setPayBoolean(booking.getPayBoolean());
		booking.setIsDeleted(booking.getIsDeleted());
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
	public Double getLastRevenue() {
		return bookingRepository.getLastRevenue();
	}

	@Override
	public Booking createBookingJson(JsonNode bookingData) {
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = mapper.convertValue(bookingData, Booking.class);
		bookingRepository.save(booking);

		BookingDetail bookingDetail = mapper.convertValue(bookingData.get("bookingDetails"), BookingDetail.class);
		bookingDetail.setBookingId(booking);
		bookingDetailRepository.save(bookingDetail);

		// cập nhật số lượng quantity
		Travel travel = travelRepository.findById(bookingDetail.getTravelId().getId()).get();
		int qtynew = travel.getQuantityNew() - bookingDetail.getTotalQuantity();
		travel.setQuantityNew(qtynew);
		travelRepository.save(travel);
		
		//Update account
		
		Account account = accountRepository.findById(booking.getBooking_account_id().getId()).get();
		account.setPhone(booking.getPhone());
		account.setAddress(booking.getAddress());
		accountRepository.save(account);
		
		return booking;
	}

	@Override
	public Double getComparedLastYear() {
		double currentMonth = bookingRepository.getRevenue();
		double lastMonth = bookingRepository.getLastRevenue();
		double result = ((currentMonth / lastMonth) * 100) - 100;
		return result;
	}

	@Override
	public String[][] getTotalPriceFromTo(String from, String to) {
		String[][] result1 = bookingRepository.getTotalPriceFromTo(from, to);
			System.out.println(result1.length);
			String[][] result = new String[2][result1.length];	
		for(int i =0 ; i<result1.length; i++) {
			result[0][result1.length- 1 - i] = result1[result1.length- 1 - i][0];
			result[1][result1.length- 1 - i] = result1[result1.length- 1 - i][1];
		}
		return result;
	}

	@Override
	public List<Booking> getAllBookingByAcId(String username) {
		return bookingRepository.getAllBookingByAcId(username);
	}

	@Override
	public Booking findByVerificationCode(String code) {
		return bookingRepository.findByVerificationCode(code);
	}

	@Override
	public Booking findBookingById(int id) {
		return bookingRepository.findBookingById(id);
	}
}
