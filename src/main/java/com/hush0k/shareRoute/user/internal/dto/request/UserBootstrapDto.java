package com.hush0k.shareRoute.user.internal.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserBootstrapDto(
        UUID id,
        String email,
        String nickname,
        String firstName,
        String lastName,
        String languageCode
) {}
