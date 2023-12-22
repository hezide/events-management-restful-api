package com.alfabet.eventsmanagementrestfulapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private LocalDateTime startTime;
    @Getter @Setter
    private LocalDateTime endTime;
    @Getter @Setter
    private String location;
    @Getter @Setter
    private String venue;
    @Getter @Setter
    private int participants;
}
