package com.hush0k.shareRoute.gamefication.internal.achievement.service;

import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementTranslation;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.*;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.*;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.AchievementConditionMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.AchievementMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementTranslationService  achievementTranslationService;
    private final AchievementConditionService achievementConditionService;
    private final AchievementConditionMapper achievementConditionMapper;

    private static final String DEFAULT_LANGUAGE = "en";

    public AchievementResponseDto createAchievement(AchievementCreateDto dto){
        Achievement achievement = achievementRepository.save(achievementMapper.toAchievement(dto));


        AchievementTranslationCreateDto defaultTranslation = AchievementTranslationCreateDto.builder()
                .name(dto.translation().name())
                .description(dto.translation().description())
                .languageCode(DEFAULT_LANGUAGE)
                .achievement(achievement)
                .build();

        AchievementConditionCreateDto defaultCondition = AchievementConditionCreateDto.builder()
                .conditionType(dto.condition().conditionType())
                .numberOfAction(dto.condition().numberOfAction())
                .achievement(achievement)
                .metadata(dto.condition().metadata())
                .build();

        achievementTranslationService.createAchievementTranslation(defaultTranslation);
        achievementConditionService.createAchievementCondition(defaultCondition);

        AchievementTranslation translation = achievementTranslationService.getByIdAndLanguage(achievement.getId(), DEFAULT_LANGUAGE);
        return achievementMapper.toAchievementResponseDto(achievement, translation);
    }

    public AchievementResponseDto updateAchievement(AchievementUpdateDto dto, UUID achievementId){
        Achievement achievement = getAchievement(achievementId);

        if (dto.condition() != null){
            achievementConditionService.updateAchievementCondition(dto.conditionId(), dto.condition());
        }
        if (dto.translation() != null){
            achievementTranslationService.updateAchievementTranslation(dto.translationId(), dto.translation());
        }

        achievementMapper.updateAchievementFromDto(dto, achievement);
        Achievement saved = achievementRepository.save(achievement);

        AchievementTranslation translation = achievementTranslationService.getByIdAndLanguage(achievementId, DEFAULT_LANGUAGE);
        return achievementMapper.toAchievementResponseDto(saved, translation);
    }

    public void deleteAchievement(UUID achievementId){
        Achievement achievement = getAchievement(achievementId);
        achievementRepository.delete(achievement);
    }

    @Transactional(readOnly = true)
    public AchievementFullResponseDto getAchievementById(UUID achievementId, String languageCode){
        Achievement achievement = getAchievement(achievementId);
        AchievementTranslation translation = achievementTranslationService.getByIdAndLanguage(achievementId, languageCode);
        AchievementConditionBriefDto condition =
                achievementConditionMapper.toAchievementConditionBriefDto(achievementConditionService.getConditionByAchievementId(achievementId));

        if(translation == null){
            translation = achievementTranslationService.getByIdAndLanguage(achievementId, DEFAULT_LANGUAGE);
        }

        return AchievementFullResponseDto.builder()
                .id(achievement.getId())
                .name(translation.getName())
                .description(translation.getDescription())
                .exp(achievement.getExp())
                .level(achievement.getLevel())
                .image(achievement.getImage())
                .condition(condition)
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<AchievementResponseDto> getAchievements(Pageable pageable, String languageCode){
        Page<Achievement> achievements = achievementRepository.findAll(pageable);

        List<UUID> achievementIds = achievements.getContent().stream()
                .map(Achievement::getId)
                .toList();

        Map<UUID, AchievementTranslation> translationMap =
                achievementTranslationService.getAllByIdAndLanguage(achievementIds, languageCode)
                        .stream()
                        .collect(Collectors.toMap(
                                AchievementTranslation -> AchievementTranslation.getAchievement().getId(),
                                Function.identity()));

        List<UUID> missingIds = achievementIds.stream()
                .filter(id -> !translationMap.containsKey(id))
                .toList();

        if(!missingIds.isEmpty()){
            Map<UUID, AchievementTranslation> fallbackMap =
                    achievementTranslationService.getAllByIdAndLanguage(missingIds, DEFAULT_LANGUAGE)
                            .stream()
                            .collect(Collectors.toMap(AchievementTranslation -> AchievementTranslation.getAchievement().getId(),
                                    Function.identity()));
            translationMap.putAll(fallbackMap);
        }

        Page<AchievementResponseDto> mapped = achievements.map(achievement -> {
            AchievementTranslation translation = translationMap.get(achievement.getId());
            return achievementMapper.toAchievementResponseDto(achievement, translation);
        });

        return PageResponse.of(mapped);
    }

    public Achievement getAchievement(UUID achievementId){
        return achievementRepository.findById(achievementId).orElseThrow(
                () -> {
                    log.warn("Could not find achievement with id {}", achievementId);
                    return new AchievementNotFoundException(achievementId);
                }
        );
    }

}
