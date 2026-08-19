package com.hush0k.shareRoute.gamefication.internal.dto.response;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;

import java.time.Instant;

public record UserArchetypeResponseDto(
        Long id,
        ArchetypeModel archetypeModel,
        boolean isOwned,
        String modelVersion,
        Double confidenceScore,
        Instant discoveredAt,
        Instant unlockedAt
) {}
