package com.hush0k.shareRoute.gamefication.internal.rank.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RankCreateDto(

        @Positive
        @NotNull
        Integer exp,

        String image

) {}
