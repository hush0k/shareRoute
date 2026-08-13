package com.hush0k.shareRoute.auth;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        String email,
        String languageCode
) {}
