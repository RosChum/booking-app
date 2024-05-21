package com.example.bookingapp.controller;

import com.example.bookingapp.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( BaseUrl.BASE_URL +"statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;
    @GetMapping
    public ResponseEntity<Resource> getStatistic(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "data.zip");
        return ResponseEntity.ok().headers(httpHeaders).body(statisticService.downloadStatistic());
    }
}
