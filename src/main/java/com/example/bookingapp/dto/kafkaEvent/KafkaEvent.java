package com.example.bookingapp.dto.kafkaEvent;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class KafkaEvent implements Serializable {

    private Long userId;

}
