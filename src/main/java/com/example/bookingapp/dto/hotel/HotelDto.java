package com.example.bookingapp.dto.hotel;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.room.RoomDto;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Название отеля не должно быть пустым")
    private String name;

    @NotBlank(message = "Описание отеля не должно быть пустым")
    private String headline;

    @NotBlank(message = "Наименование города не должно быть пустым")
    private String city;

    @NotBlank(message = "Адрес не должен быть пустым")
    private String address;

    private Integer distanceFromCenter;

    private Double rating;

    private Integer numberRatings;

    private List<RoomDto> room = new ArrayList<>();
}
