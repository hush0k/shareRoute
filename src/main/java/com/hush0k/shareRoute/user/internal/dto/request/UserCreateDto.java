package com.hush0k.shareRoute.user.internal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UserCreateDto(

        @NotBlank(message = "{user.nickname.blank}")
        @Size(min = 2, max = 64, message = "{user.nickname.size}")
        String nickname,

        @Size(min = 2, max = 64, message = "{user.firstName.size}")
        String firstName,

        @Size(min = 2, max = 64, message = "{user.lastName.size}")
        String lastName,

        @Past(message = "{user.dateOfBirth.past}")
        LocalDate dateOfBirth,

        String avatar,

        @Size(max = 1000, message = "{user.description.size}")
        String description,

        Integer countryId,

        Integer cityId

) {}
