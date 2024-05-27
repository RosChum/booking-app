package com.example.bookingapp.dto.booking;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.room.RoomShortDto;
import com.example.bookingapp.dto.user.UserShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО для бронирования")
public class BookingDto extends BaseDto {

    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;

    private UserShortDto user;

    private RoomShortDto room;
}
