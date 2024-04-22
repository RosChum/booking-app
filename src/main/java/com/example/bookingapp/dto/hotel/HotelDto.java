package com.example.bookingapp.dto.hotel;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.entity.Room;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto extends BaseDto {

    private ZonedDateTime createAt;

    private ZonedDateTime timeChanged;

    private String name;

    private String headline;

    private String city;

    private String address;

    private Integer distanceFromCenter;

    private Integer rating;

    private Integer numberRatings;

    private List<RoomDto> room = new ArrayList<>();
}
