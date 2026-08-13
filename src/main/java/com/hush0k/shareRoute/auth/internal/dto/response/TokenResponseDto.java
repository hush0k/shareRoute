package com.hush0k.shareRoute.auth.internal.dto.response;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {}
