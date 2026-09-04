package com.hush0k.shareRoute.gamefication.internal.achievement.mapper;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.UserAchievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementFullResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.UserAchievementResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {
    UserAchievementResponseDto toUserAchievementResponseDto(UserAchievement dto);
}
