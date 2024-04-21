package com.example.bookingapp.repository;

import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends BaseRepository<Booking> {
}
