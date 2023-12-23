package com.alfabet.eventsmanagementrestfulapi.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EventUpdateDTO {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String venue;
    private int participants;
}
