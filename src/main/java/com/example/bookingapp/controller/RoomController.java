package com.example.bookingapp.controller;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomSearchDto;
import com.example.bookingapp.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController implements BaseController<RoomDto, RoomSearchDto> {

    private final BaseService<RoomDto, RoomSearchDto> roomService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<RoomDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.create(dto));
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<RoomDto> update(@PathVariable Long id, @RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
    }
}
