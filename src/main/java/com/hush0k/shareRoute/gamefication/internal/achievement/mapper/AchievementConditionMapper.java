package com.hush0k.shareRoute.gamefication.internal.achievement.mapper;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementConditionCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementConditionUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionBriefDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AchievementConditionMapper {
    AchievementCondition toAchievementCondition(AchievementConditionCreateDto dto);
    AchievementConditionBriefDto toAchievementConditionBriefDto(AchievementCondition entity);
    AchievementConditionResponseDto toAchievementConditionResponseDto(AchievementCondition entity);

    List<AchievementConditionBriefDto> toBriefDto(List<AchievementCondition> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAchievementConditionFromDto(AchievementConditionUpdateDto dto, @MappingTarget AchievementCondition condition);

}
