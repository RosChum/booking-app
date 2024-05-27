package com.example.bookingapp.dto.hotel;

import com.example.bookingapp.dto.baseDto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сокращенное ДТО для отеля")
public class ShortHotelDto extends BaseDto {

    private String name;

    private String city;

    private String address;

    private Double rating;

}
