package com.example.bookingapp.service;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.entity.Booking;
import com.example.bookingapp.entity.Room;
import com.example.bookingapp.exception.BookingDatesException;
import com.example.bookingapp.exception.ContentNotFoundException;
import com.example.bookingapp.mapper.BookingMapper;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.RoomRepository;
import com.example.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookingDto createBooking(BookingDto bookingDto) {
        Map<Boolean, List<Booking>> checkAvailable = checkAvailableForBooking(bookingDto.getArrivalDate()
                , bookingDto.getDepartureDate(), bookingDto.getRoom().getId());

       if (checkAvailable.containsKey(true)) {
            Booking booking = bookingMapper.convertToEntity(bookingDto);
            booking.setUser(userRepository.findById(bookingDto.getUser().getId()).orElseThrow());
            booking.setRoom(roomRepository.findById(bookingDto.getRoom().getId()).orElseThrow());
            return bookingMapper.convertToDto(bookingRepository.save(booking));
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            checkAvailable.values().forEach(bookings -> bookings.
                    forEach(booking -> stringBuilder.append(" From ").append(booking.getArrivalDate())
                            .append(" To ").append(booking.getDepartureDate())));
            throw new BookingDatesException(MessageFormat.format("Dates are already booked, please select other dates. Booking Dates:  {0} ", stringBuilder.toString()));
        }

    }

    public Page<BookingDto> getAllBooking(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return new PageImpl<>(bookings.map(booking ->
                bookingMapper.convertToDto(booking)).toList(), pageable, bookings.getTotalElements());

    }

    private Map<Boolean, List<Booking>> checkAvailableForBooking(ZonedDateTime dateFrom, ZonedDateTime dateTo, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ContentNotFoundException(
                        MessageFormat.format("Room with id {0} not found", roomId)));

        List<Booking> existsBooking = room.getBooking().stream()
                .filter(booking -> !dateFrom.isAfter(booking.getDepartureDate()))
                .filter(booking -> !dateTo.isBefore(booking.getArrivalDate()))
                .toList();
        return Map.of(existsBooking.isEmpty(), existsBooking);

    }

}
