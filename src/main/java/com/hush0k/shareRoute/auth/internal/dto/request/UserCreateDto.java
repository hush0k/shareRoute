package com.hush0k.shareRoute.auth.internal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UserCreateDto(
        @NotNull(message = "{auth.id.notNull}")
        UUID id,

        @NotBlank(message = "{auth.email.blank}")
        @Email(message = "{auth.email.invalid}")
        String email
) {}
