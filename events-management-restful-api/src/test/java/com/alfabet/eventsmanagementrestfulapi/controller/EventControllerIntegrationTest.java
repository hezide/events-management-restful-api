package com.alfabet.eventsmanagementrestfulapi.controller;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import com.alfabet.eventsmanagementrestfulapi.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(EventController.class)
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private List<Event> mockEvents;

    @BeforeEach
    public void setUp() {
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
    public void testGetEventById() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(mockEvents.get(0)));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Launch Party"));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event newEvent = new Event() {{
            setTitle("New Event");
            setStartTime(LocalDateTime.of(2024, 1, 1, 12, 0));
            setParticipants(List.of());
            setOrganizer(new Participant());
        }};
        when(eventService.createEvent(any(Event.class))).thenReturn(newEvent);

        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Event\",\"startTime\":\"2024-01-01T12:00:00\",\"participants\":[],\"organizer\":{}}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Event"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Event updatedEvent = new Event() {{
            setEvent_id(1L);
            setTitle("Updated Event");
            setStartTime(LocalDateTime.of(2023, 12, 30, 18, 0));
            setParticipants(List.of());
            setOrganizer(new Participant());
        }};
        when(eventService.updateEvent(any(Long.class), any(Event.class))).thenReturn(updatedEvent);

        mockMvc.perform(MockMvcRequestBuilders.put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Event\",\"startTime\":\"2023-12-30T18:00:00\",\"participants\":[],\"organizer\":{}}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Event"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
