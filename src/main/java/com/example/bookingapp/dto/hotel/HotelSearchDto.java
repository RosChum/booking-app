package com.example.bookingapp.dto.hotel;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchDto extends BaseSearchDto {

    private ZonedDateTime createAt;

    private ZonedDateTime timeChanged;

    private String name;

    private String headline;

    private String city;

    private String address;

    private Integer distanceFromCenter;

    private Double rating;

    private Integer numberRatings;

    private String anyTextForSearch;
}
