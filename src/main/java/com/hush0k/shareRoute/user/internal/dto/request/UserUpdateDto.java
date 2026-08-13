package com.hush0k.shareRoute.user.internal.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record UserUpdateDto(

        @Size(min = 2, max = 64, message = "{user.nickname.size}")
        String nickname,

        @Size(min = 2, max = 64, message = "{user.firstName.size}")
        String firstName,

        @Size(min = 2, max = 64, message = "{user.lastName.size}")
        String lastName,

        String avatar,

        @Size(max = 1000, message = "{user.description.size}")
        String description,

        Integer countryId,

        Integer cityId

) {}
