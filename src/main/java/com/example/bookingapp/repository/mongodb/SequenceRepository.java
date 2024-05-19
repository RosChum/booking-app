package com.example.bookingapp.repository.mongodb;

import com.example.bookingapp.entity.mongodb.SequenceId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<SequenceId, String> {

}
