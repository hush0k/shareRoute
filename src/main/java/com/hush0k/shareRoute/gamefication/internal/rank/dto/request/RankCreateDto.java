package com.hush0k.shareRoute.gamefication.internal.rank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RankCreateDto(

        @NotBlank
        @Size(min = 1, max = 50)
        String name,

        @NotBlank
        @Size(min = 1, max = 1000)
        String description,

        @Positive
        @NotNull
        Integer exp,

        String image

) {}
