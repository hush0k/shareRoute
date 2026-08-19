package com.hush0k.shareRoute.gamefication.internal.dto.response;

import com.hush0k.shareRoute.gamefication.internal.enums.ArchetypeState;

public record ArchetypeModelResponse(
        Long id,
        String image,
        ArchetypeState archetypeState
) {}
