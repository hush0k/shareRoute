package com.hush0k.shareRoute.gamefication.internal.dto.request;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;

import java.time.Instant;

public record UserArchetypeUpdateDto(
        boolean isOwned,
        String modelVersion,
        Double confidenceScore,
        Instant unlockedAt
) {}
