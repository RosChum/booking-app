package com.example.bookingapp.service;

import com.example.bookingapp.dto.baseDto.BaseDto;
import com.example.bookingapp.dto.baseDto.BaseSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T extends BaseDto, R extends BaseSearchDto> {

    default Page<T> findAll(R dto, Pageable pageable) {
        return null;
    }

    T findById(Long id);

    T create(T dto);

    T update(Long id, T dto);

    void deleteById(Long id);

}
