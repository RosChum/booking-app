package com.example.bookingapp.controller;

import com.example.bookingapp.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseUrl.BASE_URL + "statistic")
@RequiredArgsConstructor
@Tag(name = "Ендпоинт для выгрузки статистики")
public class StatisticController {

    private final StatisticService statisticService;

    @Operation(summary = "Выгрузка статистики в формате ZIP", responses = {
            @ApiResponse(responseCode = "200", description = "Статистика выгружена"
                    , content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
            @ApiResponse(responseCode = "404", description = "Ресурс не найден"
                    , content = @Content()),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"
                    , content = @Content()),
            @ApiResponse(responseCode = "403", description = "В доступе отказано"
                    , content = @Content())
    })
    @GetMapping
    public ResponseEntity<Resource> getStatistic() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "statistic.zip");
        return ResponseEntity.ok().headers(httpHeaders).body(statisticService.downloadStatistic());
    }
}
