package com.alfabet.eventsmanagementrestfulapi.repository;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
}
