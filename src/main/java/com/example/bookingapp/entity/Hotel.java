package com.example.bookingapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {

    @Column(name = "create_at")
    private ZonedDateTime createAt;

    @Column(name = "time_changed")
    private ZonedDateTime timeChanged;

    @Column(name = "name")
    private String name;

    @Column(name = "headline")
    private String headline;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "distance_from_center")
    private Integer distanceFromCenter;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "number_ratings")
    private Integer numberRatings;

    @OneToMany(mappedBy = "hotel")
    private List<Room> room = new ArrayList<>();

}
