package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TranslationCreateDto(

        @NotNull
        Archetype archetype,

        @NotBlank
        @Size(min=3, max=200)
        String name,

        @Size(min=3, max=2000)
        String description,

        @NotBlank
        String languageCode

) {}
