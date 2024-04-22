package com.example.bookingapp.dto.booking;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto extends BaseDto {


    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;

    private RoomDto room;
}
