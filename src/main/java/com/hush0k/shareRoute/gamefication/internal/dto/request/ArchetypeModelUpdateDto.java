package com.hush0k.shareRoute.gamefication.internal.dto.request;

import com.hush0k.shareRoute.gamefication.internal.enums.ArchetypeState;

public record ArchetypeModelUpdateDto(
        String image,
        ArchetypeState archetypeState
) {}
