package com.example.bookingapp.dto.user;

import com.example.bookingapp.dto.baseDto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО для создания юзера")
public class CreateUserFormDto extends BaseDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @Email(message = "Некорректный Email")
    private String email;

}
