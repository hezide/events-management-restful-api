package com.alfabet.eventsmanagementrestfulapi.service;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import com.alfabet.eventsmanagementrestfulapi.repository.EventRepository;
import com.alfabet.eventsmanagementrestfulapi.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private ParticipantRepository participantRepository;
    @Test
    public void testCreateEvent() {
        // Mock data
        Participant hezi = new Participant() {{
            setFirstName("Hezi");
            setLastName("Debby");
            setEmail("hezided@gmail.com");
        }};
        Event mockEvent = new Event() {{
            setTitle("Sample Event");
            setStartTime(LocalDateTime.of(2023, 12, 31, 18, 0));
            setParticipants(List.of(hezi));
            setOrganizer(hezi);
        }};
        when(participantRepository.findById(hezi.getEmail())).thenReturn(Optional.empty());
        when(participantRepository.save(hezi)).thenReturn(hezi);
        when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        // Perform the service method
        Event createdEvent = eventService.createEvent(mockEvent);

        // Assertions
        assertEquals("Sample Event", createdEvent.getTitle());
    }

    @Test
    public void testGetEventById() {
        // Mock data
        Event mockEvent = new Event();
        mockEvent.setEvent_id(1L);
        mockEvent.setTitle("Sample Event");
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Perform the service method
        Optional<Event> retrievedEvent = eventService.getEventById(1L);

        // Assertions
        assertTrue(retrievedEvent.isPresent());
        assertEquals("Sample Event", retrievedEvent.get().getTitle());
    }

    @Test
    public void testUpdateEvent() {
        // Mock data
        Event existingEvent = new Event();
        existingEvent.setEvent_id(1L);
        existingEvent.setTitle("Existing Event");

        Event updatedEventData = new Event();
        updatedEventData.setTitle("Updated Event");
        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        // Perform the service method
        Event updatedEvent = eventService.updateEvent(1L, updatedEventData);

        // Assertions
        assertEquals("Updated Event", updatedEvent.getTitle());
    }

    @Test
    public void testDeleteEvent() {
        // Mock behavior of the repository
        doNothing().when(eventRepository).deleteById(1L);

        // Perform the service method
        eventService.deleteEvent(1L);

        // Verify if deleteById method was called with the correct argument
        verify(eventRepository, times(1)).deleteById(1L);
    }
}
