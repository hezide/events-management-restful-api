package com.alfabet.eventsmanagementrestfulapi.repository;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
    //todo: thisis a bad approach, fix if time allows
    @Query("SELECT e FROM Event e ORDER BY SIZE(e.participants) DESC")
    Page<Event> findAllSortedByParticipantsDesc(Specification<Event> spec, Pageable pageable);
    @Query("SELECT e FROM Event e ORDER BY SIZE(e.participants) ASC")
    Page<Event> findAllSortedByParticipantsAsc(Specification<Event> spec, Pageable pageable);
}
