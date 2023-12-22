package com.alfabet.eventsmanagementrestfulapi.service;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    //@Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long eventId, Event updatedEventData) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + eventId));

        // Update the existingEvent with updatedEventData (the entire entity)
        existingEvent.setTitle(updatedEventData.getTitle());
        existingEvent.setLocation(updatedEventData.getLocation());
        // Other fields can be updated directly in the existingEvent

        // No explicit save() needed, changes are automatically tracked and persisted upon transaction commit
        return existingEvent;
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}
