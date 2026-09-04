package com.hush0k.shareRoute.gamefication.internal.achievement.service;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementConditionCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementConditionUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionBriefDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementConditionNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.AchievementConditionMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.repository.AchievementConditionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AchievementConditionService {

    private final AchievementConditionRepository achievementConditionRepository;
    private final AchievementConditionMapper achievementConditionMapper;

    public AchievementConditionResponseDto createAchievementCondition(AchievementConditionCreateDto dto) {
        AchievementCondition achievementCondition = achievementConditionMapper.toAchievementCondition(dto);
        AchievementCondition newEntity = achievementConditionRepository.save(achievementCondition);

        return achievementConditionMapper.toAchievementConditionResponseDto(newEntity);
    }

    public AchievementConditionResponseDto updateAchievementCondition(Long id, AchievementConditionUpdateDto dto) {
        AchievementCondition condition = getCondition(id);
        achievementConditionMapper.updateAchievementConditionFromDto(dto, condition);
        return achievementConditionMapper.toAchievementConditionResponseDto(achievementConditionRepository.save(condition));
    }

    public List<AchievementCondition> getConditionsByAchievementId(UUID achievementId) {
        return achievementConditionRepository.findAllByAchievementId(achievementId);
    }

    private AchievementCondition getCondition(Long id) {
        return achievementConditionRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Could not find achievement condition with id {}", id);
                    return new AchievementConditionNotFoundException(id);
                }
        );
    }
}
