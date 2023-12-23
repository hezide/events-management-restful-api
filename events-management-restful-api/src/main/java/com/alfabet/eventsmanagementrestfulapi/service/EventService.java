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
        //todo: Convert to using DTO with MapStruct if time allows in order to prevent having to specify each property
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + eventId));

        // Update the existingEvent with updatedEventData (the entire entity)
        existingEvent.setTitle(updatedEventData.getTitle());
        existingEvent.setLocation(updatedEventData.getLocation());
        existingEvent.setDescription(updatedEventData.getDescription());
        existingEvent.setVenue(updatedEventData.getVenue());
        existingEvent.setParticipants(updatedEventData.getParticipants());
        existingEvent.setStartTime(updatedEventData.getStartTime());
        existingEvent.setEndTime(updatedEventData.getEndTime());

        // No explicit save() needed, changes are automatically tracked and persisted upon transaction commit
        return existingEvent;
    }
    @Transactional
    public Event updateEventPartially(Long id, Event updatedEventDetails) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + id));

        if (updatedEventDetails.getTitle() != null) {
            existingEvent.setTitle(updatedEventDetails.getTitle());
        }
        if (updatedEventDetails.getLocation() != null) {
            existingEvent.setLocation(updatedEventDetails.getLocation());
        }
        if (updatedEventDetails.getDescription() != null) {
            existingEvent.setDescription(updatedEventDetails.getDescription());
        }
        if (updatedEventDetails.getVenue() != null) {
            existingEvent.setVenue(updatedEventDetails.getVenue());
        }
//        if (updatedEventDetails.getParticipants() != null) {
//            existingEvent.setParticipants(updatedEventDetails.getParticipants());
//        }
        if (updatedEventDetails.getStartTime() != null) {
            existingEvent.setStartTime(updatedEventDetails.getStartTime());
        }
        if (updatedEventDetails.getEndTime() != null) {
            existingEvent.setEndTime(updatedEventDetails.getEndTime());
        }

        return existingEvent;
    }


    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
