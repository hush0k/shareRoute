package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;

public record ArchetypeUpdateDto(
        String image,
        ArchetypeState archetypeState
) {}
