package com.example.bookingapp.controller;

import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.dto.user.UserSearchDto;
import com.example.bookingapp.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements BaseController<UserDto, UserSearchDto> {

    private final BaseService<UserDto, UserSearchDto> userService;

    @GetMapping
    @Override
    public ResponseEntity<Page<UserDto>> findAll(UserSearchDto dto, Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(dto, pageable));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
