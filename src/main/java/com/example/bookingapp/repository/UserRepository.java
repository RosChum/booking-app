package com.example.bookingapp.repository;

import com.example.bookingapp.entity.User;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
}
