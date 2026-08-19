package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import java.time.Instant;

public record UserArchetypeUpdateDto(
        boolean isOwned,
        String modelVersion,
        Double confidenceScore,
        Instant unlockedAt
) {}
