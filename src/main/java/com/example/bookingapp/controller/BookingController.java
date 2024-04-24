package com.example.bookingapp.controller;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto){
        return ResponseEntity.ok(bookingService.createBooking(bookingDto));
    }

    @GetMapping
    public ResponseEntity<Page<BookingDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(bookingService.getAllBooking(pageable));
    }
}
