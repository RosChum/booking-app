package com.example.bookingapp.controller;

import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.HotelSearchDto;
import com.example.bookingapp.dto.hotel.RatingDto;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.service.BaseService;
import com.example.bookingapp.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Контроллер для сущности отель", description = "CRUD операции с сущностью отель")
public class HotelController implements BaseController<HotelDto, HotelSearchDto> {

    private final BaseService<HotelDto, HotelSearchDto> hotelService;
    private final RatingService ratingService;

    @ApiResponse(responseCode = "200", description = "Сущности найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = {Pageable.class, HotelDto.class})))
    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<HotelDto>> findAll(HotelSearchDto dto, Pageable pageable) {
        return ResponseEntity.ok(hotelService.findAll(dto, pageable));
    }
    @ApiResponse(responseCode = "200", description = "Сущность найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = HotelDto.class)))
    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<HotelDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }
    @ApiResponse(responseCode = "200", description = "Сущность создана"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = HotelDto.class)))
    @Override
    @PostMapping
    public ResponseEntity<HotelDto> create(@RequestBody @Valid HotelDto dto) {
        return ResponseEntity.ok(hotelService.create(dto));
    }
    @ApiResponse(responseCode = "200", description = "Сущность обновлена"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = HotelDto.class)))
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

    @Operation(summary = "Присвоение рейтинга отелю", responses = {
            @ApiResponse(responseCode = "200", description = "Рейтинг присвоен"
                    , content = @Content()),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)
    })
    @PostMapping("/rating/{hotelId}")
    public ResponseEntity<HotelDto> setRating(@PathVariable Long hotelId, @RequestBody @Valid RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.setRating(hotelId, ratingDto));
    }


}
