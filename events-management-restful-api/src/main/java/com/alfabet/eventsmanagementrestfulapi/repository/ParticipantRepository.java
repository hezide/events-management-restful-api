package com.alfabet.eventsmanagementrestfulapi.repository;

import com.alfabet.eventsmanagementrestfulapi.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String> {

}
