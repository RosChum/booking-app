package com.example.bookingapp.repository;

import com.example.bookingapp.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String token);
    void deleteByUserId(Long userId);
}
