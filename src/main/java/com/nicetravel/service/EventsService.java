package com.nicetravel.service;

import com.nicetravel.entity.Event;

import java.util.List;

public interface EventsService {

    List<Event> getAllEvents();

    Event getEventById(Integer id);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    void deleteEvent(int id);

    Event findEventByBookingId(int id);

    Event findEventByAccountId(int id);
}
