package com.example.bookingapp.dto.room;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.entity.Hotel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО для комнаты")
public class RoomDto extends BaseDto {

    private String name;

    private String description;

    @NotBlank(message = "Номер комнаты не должен быть пустым")
    private Integer room;

    private BigDecimal price;

    private Integer roomCapacity;

    private ShortHotelDto hotel;

    private List <BookingDto> booking;

}
