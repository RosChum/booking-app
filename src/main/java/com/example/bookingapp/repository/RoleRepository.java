package com.example.bookingapp.repository;

import com.example.bookingapp.entity.Role;
import com.example.bookingapp.entity.RoleType;
import com.example.bookingapp.repository.baserepository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByRoleType(RoleType roleType);

}
