package com.example.bookingapp.service;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.mapper.BookingMapper;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;

    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.convertToEntity(bookingDto);
        booking.setUser(booking.getUser());
        return bookingMapper.convertToDto(bookingRepository.save(booking));

    }

    public Page<BookingDto> getAllBooking(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return new PageImpl<>(bookings.map(booking ->
                bookingMapper.convertToDto(booking)).toList(), pageable, bookings.getTotalElements());

    }
}
