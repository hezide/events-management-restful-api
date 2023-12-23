package com.alfabet.eventsmanagementrestfulapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "participants")
@Getter @Setter
public class Participant {
    private String firstName;
    private String lastName;
    @Id
    @Column(unique = true, nullable = false)
    //@Email // Import javax.validation.constraints.Email
    private String email;
}
