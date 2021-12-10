package com.nicetravel.service.impl;

import com.nicetravel.entity.Event;
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
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }

    @Override
    public Event getEventById(Integer id) {
        return eventsRepository.findById(id).get();
    }

    @Override
    public Event createEvent(Event events) {
        return eventsRepository.save(events);
    }

    @Override
    public Event updateEvent(Event events) {
        return eventsRepository.save(events);
    }

    @Override
    public void deleteEvent(int id) {
        eventsRepository.deleteById(id);

    }

    @Override
    public Event findEventByBookingId(int id) {
        return eventsRepository.findEventByBookingId(id);
    }

    @Override
    public Event findEventByAccountId(int id) {
        return eventsRepository.findEventByAccountId(id);
    }
}
