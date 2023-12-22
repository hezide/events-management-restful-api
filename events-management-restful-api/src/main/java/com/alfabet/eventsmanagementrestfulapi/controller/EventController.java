package com.alfabet.eventsmanagementrestfulapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @GetMapping("/event")
    public String getEvent(){
        return "asd";
    }
}
