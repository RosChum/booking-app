package com.example.bookingapp.dto.room;

import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchDto extends BaseSearchDto {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer roomCapacity;

    private ShortHotelDto hotel;

    private BookingDto booking;


}
