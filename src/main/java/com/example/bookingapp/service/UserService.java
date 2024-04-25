package com.example.bookingapp.service;

import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.dto.user.UserSearchDto;
import com.example.bookingapp.entity.Role;
import com.example.bookingapp.entity.User;
import com.example.bookingapp.entity.User_;
import com.example.bookingapp.exception.UserAlreadyExistException;
import com.example.bookingapp.exception.UserFoundException;
import com.example.bookingapp.mapper.UserMapper;
import com.example.bookingapp.repository.RoleRepository;
import com.example.bookingapp.repository.UserRepository;
import com.example.bookingapp.specification.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto, UserSearchDto> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public Page<UserDto> findAll(UserSearchDto dto, Pageable pageable) {
        Page<User> users = userRepository.findAll(getSpecification(dto), pageable);
        return new PageImpl<>(users.map(user -> {
            UserDto userDto = userMapper.convertToDto(user);
            return userDto;
        }).toList(), pageable, users.getTotalElements());
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.convertToDto(userRepository.findById(id)
                .orElseThrow(() -> new UserFoundException(MessageFormat.format("User with id {0} not found", id))));
    }

    @Override
    public UserDto create(UserDto dto) {
        if (checkUserByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException(MessageFormat
                    .format("User with email {0} already exist ", dto.getEmail()));
        }
        User newUser = userMapper.convertToEntity(dto);
        newUser.getRoles().addAll(dto.getRoles().stream().map(role -> {
           Role exustRole = roleRepository.findByRoleType(role.getRoleType()).orElseThrow();
           exustRole.getUsers().add(newUser);
           return exustRole;
        }).collect(Collectors.toSet()));

        return userMapper.convertToDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new UserFoundException(MessageFormat.format("User with id {0} not found", id)));

        existUser.setName(dto.getName());
        if (!existUser.getEmail().equals(dto.getEmail())) {
            if (!checkUserByEmail(dto.getEmail())) {
                existUser.setEmail(dto.getEmail());
            } else {
                throw new UserAlreadyExistException(MessageFormat
                        .format("User with email {0} already exist ", dto.getEmail()));
            }
        }
        existUser.setPassword(dto.getPassword());
        return userMapper.convertToDto(userRepository.save(existUser));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private Specification<User> getSpecification(UserSearchDto userSearchDto) {
        return BaseSpecification.getBaseSpecification(userSearchDto)
                .and(BaseSpecification.like(User_.name, userSearchDto.getName()))
                .and(BaseSpecification.equal(User_.email, userSearchDto.getEmail()));
    }

    private boolean checkUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
