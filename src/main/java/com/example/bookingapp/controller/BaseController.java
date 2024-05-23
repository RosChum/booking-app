package com.example.bookingapp.controller;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BaseController<Dto extends BaseDto, SearchDto extends BaseSearchDto> {

    default ResponseEntity<Page<Dto>> findAll(SearchDto dto, Pageable pageable) {
        return null;
    }

    ResponseEntity<Dto> findById(Long id);

    ResponseEntity<Dto> create(Dto dto);

    ResponseEntity<Dto> update(Long id, Dto dto);

    void deleteById(Long id);

}
