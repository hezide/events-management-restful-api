package com.alfabet.eventsmanagementrestfulapi.service;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import com.alfabet.eventsmanagementrestfulapi.repository.EventRepository;
import com.alfabet.eventsmanagementrestfulapi.repository.ParticipantRepository;
import com.alfabet.eventsmanagementrestfulapi.specification.EventSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    //@Autowired
    public EventService(EventRepository eventRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        //Handle participants
        List<Participant> updatedParticipants = new ArrayList<>();
        for (Participant participant : event.getParticipants()) {
            updatedParticipants.add(createParticipantIfDoesntExist(participant));
        }
        event.setParticipants(updatedParticipants); // Update the event with existing participants
        //Handle organizer:
        event.setOrganizer(createParticipantIfDoesntExist(event.getOrganizer()));
        return eventRepository.save(event);
    }
    private Participant createParticipantIfDoesntExist(Participant participant){
        Optional<Participant> existingParticipant = participantRepository.findById(participant.getEmail());
        // Use the existing participant fetched from the database, if doesn't exist, create a new one
        return existingParticipant.orElseGet(() -> participantRepository.save(participant));
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
        if (updatedEventDetails.getParticipants() != null) {
            existingEvent.setParticipants(updatedEventDetails.getParticipants());
        }
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

    public List<Event> getEvents(String title, LocalDateTime startTime, LocalDateTime endTime,
                                 String location, String venue, String sort, String order, Pageable pageable) {
        //Filtering
        Specification<Event> spec = EventSpecifications.filterEvents(title, startTime, endTime, location, venue);
        //Sorting todo:: extract to a method
        Sort.Direction direction = Sort.Direction.fromString(order);
        Sort sortObj = Sort.by(direction, sort);

        if (!isValidSortProperty(sort)) {
            return eventRepository.findAll();
        }

        if (sort.equals("popularity")) {
            sortObj = Sort.by(direction, "participants.size()");
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortObj);

        return eventRepository.findAll(spec, pageable).getContent();
    }

    private Boolean isValidSortProperty(String sort) {
        List<String> sortingOptions = Arrays.asList("participants","popularity", "createdat");
        return sortingOptions.contains(sort.toLowerCase());
    }
}
