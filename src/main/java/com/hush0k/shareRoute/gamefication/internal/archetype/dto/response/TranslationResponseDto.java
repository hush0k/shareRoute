package com.hush0k.shareRoute.gamefication.internal.archetype.dto.response;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;

public record TranslationResponseDto(
       Long id,
       Archetype archetype,
       String name,
       String description,
       String languageCode
) {}
