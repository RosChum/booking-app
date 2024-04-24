package com.example.bookingapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Column(name = "arrival_date")
    private ZonedDateTime arrivalDate;

    @Column(name = "departure_date")
    private ZonedDateTime departureDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

}
