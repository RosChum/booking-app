package com.example.bookingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "hotel_room")
public class Room extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "room")
    private Integer room;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "room_capacity")
    private Integer roomCapacity;

    @OneToOne(mappedBy = "room")
    private Hotel hotel;

    @OneToOne(mappedBy = "room")
    private Booking booking;


}
