package com.alfabet.eventsmanagementrestfulapi.service;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import com.alfabet.eventsmanagementrestfulapi.repository.EventRepository;
import com.alfabet.eventsmanagementrestfulapi.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private EventService eventService;

    private List<Event> mockEvents;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Participant hezi = new Participant() {{
            setFirstName("Hezi");
            setLastName("Debby");
            setEmail("hezided@gmail.com");
        }};
        Participant bob = new Participant() {{
            setFirstName("Bob");
            setLastName("Johnson");
            setEmail("bob@example.com");
        }};

        mockEvents = Arrays.asList(
                new Event() {{
                    setEvent_id(1L);
                    setTitle("Launch Party");
                    setStartTime(LocalDateTime.of(2023, 12, 30, 18, 0));
                    setParticipants(List.of(hezi));
                    setOrganizer(hezi);
                }},
                new Event() {{
                    setEvent_id(2L);
                    setTitle("Team Meeting");
                    setStartTime(LocalDateTime.of(2023, 12, 25, 9, 0));
                    setParticipants(List.of(hezi, bob));
                    setOrganizer(hezi);
                }}
        );
    }

    @Test
    void getEventById_EventFound() {
        Long eventId = 1L;
        Event mockEvent = mockEvents.get(0);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));

        Optional<Event> retrievedEvent = eventService.getEventById(eventId);

        assertTrue(retrievedEvent.isPresent());
        assertEquals(eventId, retrievedEvent.get().getEvent_id());
    }

    @Test
    void getEventById_EventNotFound() {
        Long eventId = 10L; // Assuming this ID doesn't exist in the mock events
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        Optional<Event> retrievedEvent = eventService.getEventById(eventId);

        assertTrue(retrievedEvent.isEmpty());
    }

    @Test
    void createEvent() {
        Event event = new Event();
        Participant participant = new Participant();
        participant.setEmail("test@example.com");

        event.setOrganizer(participant);
        event.setParticipants(Collections.singletonList(participant));
        when(participantRepository.findById(participant.getEmail())).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals(participant, createdEvent.getOrganizer());
        assertTrue(createdEvent.getParticipants().contains(participant));
    }

    @Test
    void updateEvent() {
        Long eventId = 1L;
        Event existingEvent = mockEvents.get(0);
        Event updatedEvent = new Event();
        updatedEvent.setTitle("Updated Title");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        Event result = eventService.updateEvent(eventId, updatedEvent);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void updateEventPartially() {
        Long eventId = 1L;
        Event existingEvent = mockEvents.get(0);
        Event updatedEvent = new Event();
        updatedEvent.setTitle("Updated Title");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = eventService.updateEventPartially(eventId, updatedEvent);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void deleteEvent() {
        Long eventId = 1L;
        doNothing().when(eventRepository).deleteById(eventId);

        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));
    }

    //todo:: fix this test, something with the mocking
//    @Test
//    void getEvents() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));
//        Specification<Event> spec = EventSpecifications.filterEvents(null, null);
//
//        Page<Event> pagedResponse = new PageImpl<>(mockEvents);
//        when(eventRepository.findAll(spec, eq(pageable))).thenReturn(pagedResponse);
//
//        List<Event> retrievedEvents = eventService.getEvents(null, null, "createdAt", "asc", pageable);
//
//        assertNotNull(retrievedEvents);
//        assertEquals(2, retrievedEvents.size());
//    }
}
