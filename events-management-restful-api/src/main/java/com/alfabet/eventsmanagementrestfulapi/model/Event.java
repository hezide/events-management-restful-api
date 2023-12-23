package com.alfabet.eventsmanagementrestfulapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter @Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //todo:: dont include in DTO
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String venue;
    private int participants;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set the creation time on entity creation
    }
}
