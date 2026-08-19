package com.hush0k.shareRoute.gamefication.internal.rank.dto.request;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RankTranslationCreateDto(
        @NotNull
        Rank rank,

        @NotBlank
        String languageCode,

        @NotBlank
        @Size(min = 1, max = 50)
        String name,

        @NotBlank
        @Size(min = 1, max = 1000)
        String description
) {}
