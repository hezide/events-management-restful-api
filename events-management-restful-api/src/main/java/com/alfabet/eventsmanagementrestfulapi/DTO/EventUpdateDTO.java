package com.alfabet.eventsmanagementrestfulapi.DTO;

import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class EventUpdateDTO {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String venue;
    private List<Participant> participants;
}
