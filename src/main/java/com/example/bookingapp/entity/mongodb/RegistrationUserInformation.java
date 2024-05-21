package com.example.bookingapp.entity.mongodb;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "registrationUser")
public class RegistrationUserInformation {

    @CsvIgnore
    @Transient
    public static final String REGISTRATION_USER_SEQ_KEY = "regUser";

    @CsvBindByName(column = "id", required = true)
    @CsvBindByPosition( position = 0)
    @Id
    private Long id;

    @CsvBindByName(column = "userId", required = true)
    @CsvBindByPosition( position = 1)
    private Long userId;

    @CsvBindByName(column = "name", required = true)
    @CsvBindByPosition( position = 2)
    private String name;

    @CsvBindByName(column = "email", required = true)
    @CsvBindByPosition( position = 3)
    private String email;

    @CsvBindByName(column = "createAt", required = true)
    @CsvBindByPosition( position = 4)
    private Date createAt;

}
