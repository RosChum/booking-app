package com.example.bookingapp.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "bookingRoom")
public class BookingRoomInformation {

    @Transient
    public static final String BOOKING_ROOM_SEQ_KEY = "booking";

    @Id
    private Long id;

    private Long userId;

    private Long bookingId;

    private Long hotelId;

    private Date arrivalDate;

    private Date departureDate;

    private Date createAt;

}
