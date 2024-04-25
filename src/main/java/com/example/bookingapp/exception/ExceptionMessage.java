package com.example.bookingapp.exception;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionMessage implements Serializable {

    private String httpCode;
    private String message;

}
