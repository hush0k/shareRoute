package com.hush0k.shareRoute.gamefication.internal.achievement.mapper;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementTranslation;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementTranslationResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AchievementTranslationMapper {
    AchievementTranslation toAchievementTranslation(AchievementTranslationCreateDto dto);
    AchievementTranslationResponseDto toAchievementTranslationResponseDto(AchievementTranslation entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAchievementTranslationFromDto(AchievementTranslationUpdateDto dto, @MappingTarget AchievementTranslation translation);

}
