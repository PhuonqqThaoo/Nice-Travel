package com.nicetravel.service;

import com.nicetravel.entity.EventTour;

import java.util.List;

public interface EventsService {

    List<EventTour> getAllEvents();

    EventTour getEventById(Integer id);

    EventTour createEvent(EventTour events);

    EventTour updateEvent(EventTour events);

    void deleteEvent(int id);
}
