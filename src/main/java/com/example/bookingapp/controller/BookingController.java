package com.example.bookingapp.controller;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Tag(name = "Контроллер по управлению бронированием", description = "Создание бронирования и выгрузка всех броней")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Создание бронирования", responses = {
            @ApiResponse(responseCode = "200", description = "Бронирование создано"
                    , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(allOf = BookingDto.class))),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content())
    })
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDto));
    }
    @Operation(summary = "Получение всех записей бронирования", responses = {
            @ApiResponse(responseCode = "200", description = "Записи получены"
                    , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(allOf = {Pageable.class, BookingDto.class}))),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content())
    })
    @GetMapping
    public ResponseEntity<Page<BookingDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBooking(pageable));
    }
}
