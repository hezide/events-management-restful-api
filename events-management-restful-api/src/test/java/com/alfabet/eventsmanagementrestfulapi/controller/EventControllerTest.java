package com.alfabet.eventsmanagementrestfulapi.controller;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import com.alfabet.eventsmanagementrestfulapi.service.EventService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private List<Event> mockEvents;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

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
    public void testGetEvents() throws Exception {
        when(eventService.getEvents(null, null, "createdAt", "asc", PageRequest.of(0, 10)))
                .thenReturn(mockEvents);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testGetEventById_EventFound() throws Exception {
        Long eventId = 1L;
        Event event = mockEvents.get(0);
        when(eventService.getEventById(eventId)).thenReturn(Optional.of(event));

        mockMvc.perform(get("/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.event_id").value(event.getEvent_id()));
    }

    @Test
    public void testGetEventById_EventNotFound() throws Exception {
        Long eventId = 10L; // Assuming this ID doesn't exist
        when(eventService.getEventById(eventId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/events/{id}", eventId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found"));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event newEvent = new Event();
        newEvent.setTitle("New Event");

        when(eventService.createEvent(any(Event.class))).thenReturn(newEvent);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Event\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Event"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Long eventId = 1L;
        Event updatedEvent = mockEvents.get(0);
        updatedEvent.setTitle("Updated Event");

        when(eventService.updateEvent(eq(eventId), any(Event.class))).thenReturn(updatedEvent);

        mockMvc.perform(put("/events/{id}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Event\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Event"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Long eventId = 1L;
        doNothing().when(eventService).deleteEvent(eventId);

        mockMvc.perform(delete("/events/{id}", eventId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvent_NotFound() throws Exception {
        Long eventId = 10L; // Assuming this ID doesn't exist
        doThrow(new NoSuchElementException()).when(eventService).deleteEvent(eventId);

        mockMvc.perform(delete("/events/{id}", eventId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEvent_InternalServerError() throws Exception {
        Long eventId = 1L;
        doThrow(new RuntimeException()).when(eventService).deleteEvent(eventId);

        mockMvc.perform(delete("/events/{id}", eventId))
                .andExpect(status().isInternalServerError());
    }
}
