package com.alfabet.eventsmanagementrestfulapi.specification;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {
    public static Specification<Event> filterEvents(
            String locationFilter, String venueFilter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (locationFilter != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")), locationFilter.toLowerCase()));
            }

            if (venueFilter != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("venue")), venueFilter.toLowerCase()));
            }

            return predicate;
        };
    }
}
