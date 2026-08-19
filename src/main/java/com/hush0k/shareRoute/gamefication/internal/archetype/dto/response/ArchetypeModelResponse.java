package com.hush0k.shareRoute.gamefication.internal.archetype.dto.response;

import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;

public record ArchetypeModelResponse(
        Long id,
        String image,
        ArchetypeState archetypeState
) {}
