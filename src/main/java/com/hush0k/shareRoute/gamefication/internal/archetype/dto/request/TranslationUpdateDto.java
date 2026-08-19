package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import jakarta.validation.constraints.Size;

public record TranslationUpdateDto(
        @Size(min=3, max=200)
        String name,

        @Size(min=3, max=2000)
        String descripton
) {}
