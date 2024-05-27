package com.example.bookingapp.controller;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface BaseController<Dto extends BaseDto, SearchDto extends BaseSearchDto> {
    @Operation(summary = "Получение всех сущностей", responses = {
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)
    })
    default ResponseEntity<Page<Dto>> findAll(SearchDto dto, Pageable pageable) {
        return null;
    }

    @Operation(summary = "Получение сущности по id", responses = {

            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)
    })
    ResponseEntity<Dto> findById(Long id);

    @Operation(summary = "Создание сущности", responses = {

            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)
    })
    ResponseEntity<Dto> create(Dto dto);

    @Operation(summary = "Обновление сущности по id", responses = {

            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)
    })
    ResponseEntity<Dto> update(Long id, Dto dto);

    @Operation(summary = "Удаление сущности по id", responses = {
            @ApiResponse(responseCode = "200", description = "Сущность удалена"
                    , content = @Content()),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content)})
    void deleteById(Long id);

}
