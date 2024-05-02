package com.example.bookingapp.controller;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.HotelSearchDto;
import com.example.bookingapp.dto.hotel.RatingDto;
import com.example.bookingapp.service.BaseService;
import com.example.bookingapp.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController implements BaseController<HotelDto, HotelSearchDto> {

    private final BaseService<HotelDto, HotelSearchDto> hotelService;
    private final RatingService ratingService;

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<HotelDto>> findAll(HotelSearchDto dto, Pageable pageable) {
        return ResponseEntity.ok(hotelService.findAll(dto, pageable));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<HotelDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<HotelDto> create(@RequestBody @Valid HotelDto dto) {
        return ResponseEntity.ok(hotelService.create(dto));
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<HotelDto> update(@PathVariable Long id, @RequestBody HotelDto dto) {
        return ResponseEntity.ok(hotelService.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        hotelService.deleteById(id);
    }

    @PostMapping("/rating/{hotelId}")
    private ResponseEntity<Void> setRating(@PathVariable Long hotelId, @RequestBody @Valid RatingDto ratingDto) {
        ratingService.setRating(hotelId, ratingDto);
        return ResponseEntity.ok().build();
    }
}
