package com.hush0k.shareRoute.gamefication.internal.archetype.dto.response;

import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;
import lombok.Builder;

@Builder
public record ArchetypeSummaryDto(
        Long id,
        String image,
        ArchetypeState archetypeState,
        String name,
        String description
) {}
