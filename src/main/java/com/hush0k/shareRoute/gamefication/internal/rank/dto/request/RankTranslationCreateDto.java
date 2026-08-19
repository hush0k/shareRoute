package com.hush0k.shareRoute.gamefication.internal.rank.dto.request;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;

public record RankTranslationCreateDto(
        Rank rank,
        String languageCode,
        String name,
        String description
) {}
