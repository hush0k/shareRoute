package com.hush0k.shareRoute.gamefication.internal.achievement.service;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.UserAchievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.UserAchievementCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.UserAchievementResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.UserAchievementMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserAchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementService achievementService;

    private static final String DEFAULT_LANGUAGE = "en";

    public UserAchievementResponseDto createUserAchievement(UserAchievementCreateDto dto) {
        Achievement achievement = achievementService.getAchievement(dto.achievementId());
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(dto.userId())
                .build();

        return userAchievementMapper.toUserAchievementResponseDto(userAchievementRepository.save(userAchievement));
    }
}
