package com.hush0k.shareRoute.gamefication.internal.archetype.dto.response;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;

import java.time.Instant;

public record UserArchetypeResponseDto(
        Long id,
        ArchetypeSummaryDto archetype,
        boolean isOwned,
        String modelVersion,
        Double confidenceScore,
        Instant discoveredAt,
        Instant unlockedAt
) {}
