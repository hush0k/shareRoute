package com.hush0k.shareRoute.gamefication.internal.dto.response;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;

public record TranslationResponseDto(
       Long id,
       ArchetypeModel archetypeModel,
       String name,
       String description,
       String languageCode
) {}
