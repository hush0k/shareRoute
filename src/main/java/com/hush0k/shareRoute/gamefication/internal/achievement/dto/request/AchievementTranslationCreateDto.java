package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
public record AchievementTranslationCreateDto(
        Achievement achievement,
        @NotBlank @Size(max = 10) String languageCode,
        @NotBlank @Size(max = 100) String name,
        @Size(max = 1000) String description
) {}
