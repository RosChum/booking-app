package com.example.bookingapp.repository.mongodb;

import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationUserRepository extends MongoRepository<RegistrationUserInformation, Long> {
}
