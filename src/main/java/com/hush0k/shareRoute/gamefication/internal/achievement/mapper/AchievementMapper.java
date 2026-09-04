package com.hush0k.shareRoute.gamefication.internal.achievement.mapper;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementTranslation;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementFullResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    @Mapping(target = "condition", ignore = true)
    Achievement toAchievement(AchievementCreateDto dto);

    Achievement toAchievementFromFullDto(AchievementFullResponseDto dto);

    @Mapping(target = "id", source = "achievement.id")
    AchievementResponseDto toAchievementResponseDto(Achievement achievement, AchievementTranslation translation);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            unmappedSourcePolicy = ReportingPolicy.IGNORE
    )
    @Mapping(target = "condition", ignore = true)
    void updateAchievementFromDto(AchievementUpdateDto dto, @MappingTarget Achievement achievement);
}
