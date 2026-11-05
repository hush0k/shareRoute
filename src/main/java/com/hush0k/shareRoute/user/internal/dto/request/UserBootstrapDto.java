package com.hush0k.shareRoute.user.internal.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record UserBootstrapDto(
        UUID id,
        String email,
        String nickname,
        String firstName,
        String lastName,
        String languageCode,
        LocalDate dateOfBirth,
        String description,
        Integer countryId,
        Integer cityId
) {}
