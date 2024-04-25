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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BookingDto createBooking(BookingDto bookingDto) {

        if (checkBookingDate(bookingDto.getArrivalDate(), bookingDto.getDepartureDate(), bookingDto.getRoom().getId())) {

            Booking booking = bookingMapper.convertToEntity(bookingDto);
            booking.setUser(userRepository.findById(bookingDto.getUser().getId()).orElseThrow());
            booking.setRoom(roomRepository.findById(bookingDto.getRoom().getId()).orElseThrow());
            return bookingMapper.convertToDto(bookingRepository.save(booking));
        } else {
//            Map<String, List<ZonedDateTime>> bookingDate = new HashMap<>();
//            getBookingByRoomId(bookingDto.getRoom().getId()).forEach(booking -> {
//                bookingDate.put("From", List.of(booking.getArrivalDate()));
//                bookingDate.put("To", List.of(booking.getDepartureDate()));
//            });bookingDate.get("From"), bookingDate.get("To"))
            throw new BookingDatesException(MessageFormat.format("Dates are already booked, please select other dates. BookingDates: From {0} To {1}", bookingDto.getArrivalDate(),bookingDto.getDepartureDate()));
        }

    }

    public Page<BookingDto> getAllBooking(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return new PageImpl<>(bookings.map(booking ->
                bookingMapper.convertToDto(booking)).toList(), pageable, bookings.getTotalElements());

    }

    private boolean checkBookingDate(ZonedDateTime dateFrom, ZonedDateTime dateTo, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ContentNotFoundException(
                        MessageFormat.format("Room with id {0} not found", roomId)));

        List<Booking> existsBooking = room.getBooking().stream()
                .filter(booking -> dateFrom.isBefore(booking.getDepartureDate()))
                .filter(booking -> dateTo.isBefore(booking.getArrivalDate()))
                .toList();
        return existsBooking.isEmpty();

    }

    private List<Booking> getBookingByRoomId(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow().getBooking();
    }
}
