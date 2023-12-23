package com.alfabet.eventsmanagementrestfulapi.controller;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
//    @GetMapping
//    public ResponseEntity<List<Event>> getAllEvents(){
//        List<Event> allEvents = eventService.getAllEvents();
//        if (allEvents != null) {
//            return new ResponseEntity<>(allEvents, HttpStatus.OK);
//        } else {
//            //todo: add more data to the error
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String venue,
            @RequestParam(required = false, defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        //todo:: add try catch
        List<Event> events = eventService.getEvents(title, startTime, endTime, location, venue,createdAt, sort, order, PageRequest.of(page, size));
        return ResponseEntity.ok(events);
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

    @PutMapping("{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event updatedEventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, updatedEventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PatchMapping("{id}")
    public ResponseEntity<?> updateEventPartially(@PathVariable Long id, @RequestBody Event updatedEventDetails) {
        try {
            Event updatedEvent = eventService.updateEventPartially(id, updatedEventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id){
        try {
            //todo:: check if already exist
            eventService.deleteEvent(id);
            return ResponseEntity.status( HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            //todo:: this doesn't get called, find another way to say that there was no such element
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
