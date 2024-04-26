package com.example.bookingapp.dto.room;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomShortDto extends BaseDto {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer roomCapacity;

    private ShortHotelDto hotel;
}
