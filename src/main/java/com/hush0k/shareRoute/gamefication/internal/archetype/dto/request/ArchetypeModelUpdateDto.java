package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;

public record ArchetypeModelUpdateDto(
        String image,
        ArchetypeState archetypeState
) {}
