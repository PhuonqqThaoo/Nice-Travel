package com.nicetravel.dao;

import com.nicetravel.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailDAO extends JpaRepository<BookingDetail, Integer> {
}
