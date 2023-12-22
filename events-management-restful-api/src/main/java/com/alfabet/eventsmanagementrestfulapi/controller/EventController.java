package com.alfabet.eventsmanagementrestfulapi.controller;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        if (createdEvent != null) {
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } else {
            //todo: add more data to the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> allEvents = eventService.getAllEvents();
        if (allEvents != null) {
            return new ResponseEntity<>(allEvents, HttpStatus.OK);
        } else {
            //todo: add more data to the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEventById(@PathVariable final Long id){
        try {
            Event event = eventService.getEventById(id)
                    .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + id));
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/events/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event updatedEventDetails) {
        try {
            Event existingEvent = eventService.getEventById(id)
                    .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + id));

            // Update the existingEvent with details from updatedEventDetails
            // Example:
            existingEvent.setTitle(updatedEventDetails.getTitle());
            existingEvent.setLocation(updatedEventDetails.getLocation());
            existingEvent.setStartTime(updatedEventDetails.getStartTime());

            Event updatedEvent = eventService.updateEvent(id, existingEvent);
            return ResponseEntity.ok(updatedEvent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
