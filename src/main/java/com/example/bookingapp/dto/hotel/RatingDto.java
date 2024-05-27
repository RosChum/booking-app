package com.example.bookingapp.dto.hotel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО для рейтинга отеля")
public class RatingDto {

    @Min(value = 1, message = "Rating mast be from 1 to 5")
    @Max(value = 5, message = "Rating mast be from 1 to 5")
    private Integer rating;
}
