package com.example.bookingapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;

@RedisHash("refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {


    @Id
    @Indexed
    private Long id;

    @Indexed
    private String refreshToken;

    @Indexed
    private Long userId;

    @Indexed
    private Instant expireDate;


}
