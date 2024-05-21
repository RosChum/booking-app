package com.example.bookingapp.entity.mongodb;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
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

    @CsvIgnore
    @Transient
    public static final String BOOKING_ROOM_SEQ_KEY = "booking";
    @CsvBindByPosition( position = 0)
    @Id
    private Long id;
    @CsvBindByPosition( position = 1)
    private Long userId;
    @CsvBindByPosition( position = 2)
    private Long bookingId;
    @CsvBindByPosition( position = 3)
    private Long hotelId;
    @CsvBindByPosition( position = 4)
    private Date arrivalDate;
    @CsvBindByPosition( position = 5)
    private Date departureDate;
    @CsvBindByPosition( position = 6)
    private Date createAt;

}
