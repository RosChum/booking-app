package com.example.bookingapp.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "registrationUser")
public class RegistrationUserInformation {

    @Transient
    public static final String REGISTRATION_USER_SEQ_KEY = "regUser";

    @Id
    private Long id;

    private Long userId;

    private String name;

    private String email;

    private ZonedDateTime createAt;

}
