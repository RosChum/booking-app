package com.example.bookingapp.repository;

import com.example.bookingapp.entity.User;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
