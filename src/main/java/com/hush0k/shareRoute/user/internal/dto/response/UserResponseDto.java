package com.hush0k.shareRoute.user.internal.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nickname,
        String email,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String avatar,
        Integer countryId,
        Integer cityId,
        Integer rankId,
        int exp,
        String language,
        Instant createdAt,
        Instant updatedAt
) {}
