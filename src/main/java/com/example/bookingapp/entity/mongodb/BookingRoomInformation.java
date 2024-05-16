package com.example.bookingapp.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "bookingRoom")
public class BookingRoomInformation {

    @Transient
    public static final String BOOKING_ROOM_SEQ_KEY = "booking";

    @Id
    private Long Id;

    private Long userId;

    private Long bookingId;

    private Long hotelId;

    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;

    private ZonedDateTime createAt;

}
