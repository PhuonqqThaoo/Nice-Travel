package com.nicetravel.service.impl;

import com.nicetravel.entity.EventTour;
import com.nicetravel.repository.EventsRepository;
import com.nicetravel.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsServiceImpl implements EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    @Override
    public List<EventTour> getAllEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public EventTour getEventById(Integer id) {
        return eventsRepository.findById(id).get();
    }

    @Override
    public EventTour createEvent(EventTour events) {
        return eventsRepository.save(events);
    }

    @Override
    public EventTour updateEvent(EventTour events) {
        return eventsRepository.save(events);
    }

    @Override
    public void deleteEvent(int id) {
        eventsRepository.deleteById(id);

    }
}
