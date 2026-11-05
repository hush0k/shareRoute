package com.hush0k.shareRoute.gamefication.internal.achievement.service;

import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.UserAchievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.UserAchievementCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.UserAchievementResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.UserAchievementMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;
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

    public void incrementToOne(UUID userId, UUID achievementId) {
        Achievement achievement = achievementService.getAchievement(achievementId);
        Long maxCount = achievement.getCondition().getNumberOfAction();
        UserAchievement userAchievement = userAchievementRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> UserAchievement.builder()
                        .achievement(achievement)
                        .userId(userId)
                        .build());

        if (userAchievement.getCompletedCount() < maxCount) {
            userAchievement.setCompletedCount(userAchievement.getCompletedCount() + 1);

            if (Objects.equals(userAchievement.getCompletedCount(), maxCount)) {
                userAchievement.setIsAchieved(true);
                userAchievement.setAchievedAt(Instant.now());
            }
            userAchievementRepository.save(userAchievement);
        }
    }

    @Transactional(readOnly = true)
    public PageResponse<UserAchievementResponseDto> getUserAchievements(UUID userId, Pageable pageable) {
        Page<UserAchievement> userAchievements = userAchievementRepository.findAllByUserId(userId, pageable);

        Page<UserAchievementResponseDto> mapped =
                userAchievements.map(userAchievementMapper::toUserAchievementResponseDto);

        return PageResponse.of(mapped);
    }
}
