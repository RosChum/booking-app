package com.example.bookingapp.repository.mongodb;

import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRoomRepository extends MongoRepository<BookingRoomInformation,Long> {
}
