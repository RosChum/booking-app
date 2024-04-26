package com.example.bookingapp.repository;

import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends BaseRepository<Booking> {

    Optional<Booking> findByArrivalDateAfterAndDepartureDateBefore(ZonedDateTime to, ZonedDateTime from);
}
