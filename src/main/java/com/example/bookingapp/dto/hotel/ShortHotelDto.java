package com.example.bookingapp.dto.hotel;

import com.example.bookingapp.dto.baseDto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortHotelDto extends BaseDto {

    private String name;

    private String city;

    private String address;

    private Integer rating;

}
