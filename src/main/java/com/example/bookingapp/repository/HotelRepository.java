package com.example.bookingapp.repository;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends BaseRepository<Hotel> {
}
