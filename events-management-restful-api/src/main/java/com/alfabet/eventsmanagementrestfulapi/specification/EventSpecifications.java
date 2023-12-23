package com.alfabet.eventsmanagementrestfulapi.specification;

import com.alfabet.eventsmanagementrestfulapi.model.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecifications {
    public static Specification<Event> filterEvents(
            String title, LocalDateTime startTime, LocalDateTime endTime,
            String location, String venue, LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (title != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), title.toLowerCase()));
            }

            if (startTime != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime));
            }

            if (createdAt != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt));
            }

            //todo:: think if i want to include
            if (endTime != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime));
            }

            if (location != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")), location.toLowerCase()));
            }

            if (venue != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("venue")), venue.toLowerCase()));
            }

            return predicate;
        };
    }
}
