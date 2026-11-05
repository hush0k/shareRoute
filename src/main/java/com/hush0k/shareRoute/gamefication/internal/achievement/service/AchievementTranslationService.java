package com.hush0k.shareRoute.gamefication.internal.achievement.service;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementTranslation;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementTranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementTranslationNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.achievement.mapper.AchievementTranslationMapper;
import com.hush0k.shareRoute.gamefication.internal.achievement.repository.AchievementTranslationRepository;
import lombok.RequiredArgsConstructor;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AchievementTranslationService {

    private final AchievementTranslationRepository achievementsTranslationRepository;
    private final AchievementTranslationMapper achievementTranslationMapper;

    public AchievementTranslationResponseDto createAchievementTranslation(AchievementTranslationCreateDto dto){
        AchievementTranslation translation = achievementTranslationMapper.toAchievementTranslation(dto);

        AchievementTranslation saved = achievementsTranslationRepository.save(translation);
        return achievementTranslationMapper.toAchievementTranslationResponseDto(saved);
    }

    public AchievementTranslationResponseDto updateAchievementTranslation(UUID id, AchievementTranslationUpdateDto dto){
        AchievementTranslation translation = getTranslation(id);
        achievementTranslationMapper.updateAchievementTranslationFromDto(dto, translation);
        return achievementTranslationMapper.toAchievementTranslationResponseDto(achievementsTranslationRepository.save(translation));
    }

    public AchievementTranslationResponseDto updateAchievementTranslation(UUID achievementId, String languageCode, AchievementTranslationUpdateDto dto){
        AchievementTranslation translation = getTranslation(achievementId, languageCode);
        achievementTranslationMapper.updateAchievementTranslationFromDto(dto, translation);
        return achievementTranslationMapper.toAchievementTranslationResponseDto(achievementsTranslationRepository.save(translation));
    }

    public void deleteAchievementTranslation(UUID achievementId, String languageCode){
        achievementsTranslationRepository.delete(getTranslation(achievementId, languageCode));
    }

    @Transactional(readOnly = true)
    public AchievementTranslationResponseDto getAchievementTranslation(UUID achievementId, String languageCode){
        return achievementTranslationMapper.toAchievementTranslationResponseDto(getTranslation(achievementId, languageCode));
    }

    @Transactional(readOnly = true)
    public PageResponse<AchievementTranslationResponseDto> getAchievementTranslations(UUID achievementId, Pageable pageable){
        return PageResponse.of(
                achievementsTranslationRepository.findAllByAchievementId(achievementId, pageable)
                        .map(achievementTranslationMapper::toAchievementTranslationResponseDto));
    }

    @Transactional(readOnly = true)
    public AchievementTranslation getByIdAndLanguage(UUID achievementId, String languageCode){
        return
                achievementsTranslationRepository.findByAchievementIdAndLanguageCode(achievementId, languageCode);
    }

    @Transactional(readOnly = true)
    public List<AchievementTranslation> getAllByIdAndLanguage(List<UUID> achievementIds, String languageCode){
        return
                achievementsTranslationRepository.findByAchievementIdInAndLanguageCode(achievementIds, languageCode);
    }

    private AchievementTranslation getTranslation(UUID id){
        return achievementsTranslationRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Could not find achievement translation with id {}", id);
                    return new AchievementTranslationNotFoundException(id);
                }
        );
    }

    private AchievementTranslation getTranslation(UUID achievementId, String languageCode){
        return achievementsTranslationRepository.findOneByAchievementIdAndLanguageCode(achievementId, languageCode).orElseThrow(
                () -> {
                    log.warn("Could not find translation for achievement {} and language {}", achievementId, languageCode);
                    return new AchievementTranslationNotFoundException(achievementId, languageCode);
                }
        );
    }
}
