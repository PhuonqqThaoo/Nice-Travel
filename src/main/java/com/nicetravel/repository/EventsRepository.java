package com.nicetravel.repository;

import com.nicetravel.entity.Booking;
import com.nicetravel.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT u FROM Event u WHERE u.booking.id = ?1")
    Event findEventByBookingId(int id);

    @Query("SELECT u FROM Event u WHERE u.account.id = ?1")
    Event findEventByAccountId(int id);
}
