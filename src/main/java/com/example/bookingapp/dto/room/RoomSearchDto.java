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
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchDto extends BaseSearchDto {

    private String name;

    private String description;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer roomCapacity;

    private Long hotelId;

    private ZonedDateTime arrivalDate;

    private ZonedDateTime departureDate;


}
