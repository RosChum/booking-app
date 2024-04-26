package com.example.bookingapp.dto.booking;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.room.RoomShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingShortDto extends BaseDto {

    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;

    private RoomShortDto room;
}
