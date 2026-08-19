package com.hush0k.shareRoute.gamefication.internal.rank.dto.response;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;

public record RankTranslationResponseDto(
        Long id,
        Rank rank,
        String name,
        String description
) {}
