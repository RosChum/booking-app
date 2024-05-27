package com.example.bookingapp.controller;

import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomSearchDto;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.service.BaseService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController implements BaseController<RoomDto, RoomSearchDto> {

    private final BaseService<RoomDto, RoomSearchDto> roomService;
    @ApiResponse(responseCode = "200", description = "Сущности найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = {Pageable.class, RoomDto.class})))
    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<RoomDto>> findAll(RoomSearchDto dto, Pageable pageable) {
        return ResponseEntity.ok(roomService.findAll(dto, pageable));
    }    @ApiResponse(responseCode = "200", description = "Сущность найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = RoomDto.class)))
    @GetMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<RoomDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }
    @ApiResponse(responseCode = "200", description = "Сущность создана"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = RoomDto.class)))
    @PostMapping
    @Override
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.create(dto));
    }
    @ApiResponse(responseCode = "200", description = "Сущность обновлена"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = RoomDto.class)))
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
