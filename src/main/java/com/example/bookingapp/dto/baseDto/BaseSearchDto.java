package com.example.bookingapp.dto.baseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseSearchDto implements Serializable {

    private Long id;

    private Boolean isDeleted;
}
