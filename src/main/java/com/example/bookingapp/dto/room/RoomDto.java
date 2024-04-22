package com.example.bookingapp.dto.room;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.entity.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto extends BaseDto {

    private String name;

    private String description;

    private Integer room;

    private BigDecimal price;

    private Integer roomCapacity;

    private ShortHotelDto hotel;

    private BookingDto booking;

}
