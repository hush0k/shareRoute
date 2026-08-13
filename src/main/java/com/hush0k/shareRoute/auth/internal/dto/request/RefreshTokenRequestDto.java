package com.hush0k.shareRoute.auth.internal.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(
        @NotBlank
        String refreshToken
) {}
