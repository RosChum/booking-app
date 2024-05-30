package com.example.bookingapp.dto.baseDto;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseDto implements Serializable {

    private Long id;

    private Boolean isDeleted;
}
