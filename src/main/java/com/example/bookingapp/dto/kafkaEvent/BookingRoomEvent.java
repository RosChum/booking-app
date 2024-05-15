package com.example.bookingapp.dto.kafkaEvent;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BookingRoomEvent extends KafkaEvent {

    private Long bookingId;

    private Long hotelId;

    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;


}
