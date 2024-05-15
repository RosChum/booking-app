package com.example.bookingapp.dto.kafkaEvent;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RegistrationUserEvent extends KafkaEvent {

    private String email;

    private String name;

    private ZonedDateTime createAt;

}
