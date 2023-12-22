package com.alfabet.eventsmanagementrestfulapi.repository;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query methods can be added here if needed
}
