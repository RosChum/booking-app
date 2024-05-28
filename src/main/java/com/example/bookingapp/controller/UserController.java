package com.example.bookingapp.controller;

import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.dto.user.UserSearchDto;
import com.example.bookingapp.service.BaseService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseUrl.BASE_URL + "user")
@RequiredArgsConstructor
@Tag(name = "Контроллер для управления сущностью User", description = "Реализация CRUD для управления пользователями")
public class UserController implements BaseController<UserDto, UserSearchDto> {

    private final BaseService<UserDto, UserSearchDto> userService;

    @ApiResponse(responseCode = "200", description = "Сущности найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = {Pageable.class, UserDto.class})))
    @GetMapping
    @Override
    public ResponseEntity<Page<UserDto>> findAll(UserSearchDto dto, Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(dto, pageable));
    }
    @ApiResponse(responseCode = "200", description = "Сущность найден"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = UserDto.class)))
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @ApiResponse(responseCode = "200", description = "Сущность создана"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = UserDto.class)))
    @PostMapping
    @Override
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @ApiResponse(responseCode = "200", description = "Сущность обновлена"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(allOf = UserDto.class)))
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
