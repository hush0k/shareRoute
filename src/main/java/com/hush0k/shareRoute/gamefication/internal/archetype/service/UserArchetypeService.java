package com.hush0k.shareRoute.gamefication.internal.archetype.service;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.UserArchetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.UserArchetypeCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.UserArchetypeUpdateDto;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.UserArchetypeResponseDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.NotPublishedArchetypeException;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.UserArchetypeNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.ArchetypeMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.UserArchetypeMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.UserArchetypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserArchetypeService {

    private final UserArchetypeRepository userArchetypeRepository;
    private final UserArchetypeMapper userArchetypeMapper;
    private final ArchetypeService archetypeService;
    private final ArchetypeMapper archetypeMapper;
    private final ArchetypeTranslationService archetypeTranslationService;

    public UserArchetypeResponseDto createUserArchetype(UserArchetypeCreateDto dto, UUID userId, Long archetypeId){
        Archetype archetype = archetypeMapper.toArchetypeFromResponse(archetypeService.getArchetypeById(archetypeId));
        UserArchetype newUserArchetype = userArchetypeMapper.toUserArchetype(dto);
        if (archetype.getArchetypeState() != ArchetypeState.PUBLISHED) {
            log.warn("Attempt to assign not published archetype with id {}", archetype.getId());
            throw new NotPublishedArchetypeException(archetype.getId());
        }
        newUserArchetype.setUserId(userId);
        newUserArchetype.setArchetype(archetype);
        UserArchetype userArchetype = userArchetypeRepository.save(newUserArchetype);
        return userArchetypeMapper.toUserArchetypeResponseDto(userArchetype);
    }

    public UserArchetypeResponseDto updateUserArchetype(UserArchetypeUpdateDto dto, Long userArchetypeId){
        UserArchetype userArchetype =getUserArchetype(userArchetypeId);
        userArchetypeMapper.updateUserArchetype(dto,userArchetype);
        return userArchetypeMapper.toUserArchetypeResponseDto(userArchetypeRepository.save(userArchetype));
    }

    public void deleteUserArchetype(Long userArchetypeId){
        UserArchetype userArchetype = getUserArchetype(userArchetypeId);
        userArchetypeRepository.delete(userArchetype);
    }

    @Transactional(readOnly = true)
    public UserArchetypeResponseDto getUserArchetypeById(Long userArchetypeId){
        UserArchetype userArchetype = getUserArchetype(userArchetypeId);
        return userArchetypeMapper.toUserArchetypeResponseDto(userArchetype);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserArchetypeResponseDto> getUserArchetypesByUserId(Pageable pageable, UUID userId, String language) {
        Page<UserArchetype> userArchetypes = userArchetypeRepository.findByUserId(userId, pageable);

        List<Long> archetypeIds = userArchetypes.stream()
                .map(ua -> ua.getArchetype().getId())
                .distinct()
                .toList();

        List<ArchetypeTranslation> translations =
                archetypeTranslationService.getArchetypeTranslationsByLanguage(archetypeIds, language);

        Map<Long, ArchetypeTranslation> translationByArchetypeId = translations.stream()
                .collect(Collectors.toMap(t -> t.getArchetype().getId(), t -> t));

        List<Long> missingIds = archetypeIds.stream()
                .filter(id -> !translationByArchetypeId.containsKey(id))
                .toList();

        if (!missingIds.isEmpty()) {
            List<ArchetypeTranslation> fallbackTranslations =
                    archetypeTranslationService.getArchetypeTranslationsByLanguage(missingIds, "en");
            fallbackTranslations.forEach(t -> translationByArchetypeId.put(t.getArchetype().getId(), t));
        }

        Page<UserArchetypeResponseDto> mapped = userArchetypes.map(ua ->
                userArchetypeMapper.toUserArchetypeResponseDto(
                        ua,
                        translationByArchetypeId.get(ua.getArchetype().getId())
                )
        );

        return PageResponse.of(mapped);
    }

    public UserArchetypeResponseDto unlock(UUID userId, Long archetypeId){
        UserArchetype userArchetype = userArchetypeRepository.findByUserIdAndArchetypeId(userId, archetypeId)
                .orElseThrow(
                        () -> {
                            log.warn("Attempt to unlock archetype with id {} which they don't have", archetypeId);
                            return new UserArchetypeNotFoundException(archetypeId);
                        }
                );
        if (userArchetype.getUnlockedAt() == null){
            userArchetype.setUnlockedAt(Instant.now());
            userArchetype.setIsOwned(true);
        }

        return userArchetypeMapper.toUserArchetypeResponseDto(userArchetypeRepository.save(userArchetype));
    }


    private UserArchetype getUserArchetype(Long userArchetypeId){
        return userArchetypeRepository.findById(userArchetypeId)
                .orElseThrow(
                        () -> {
                            log.warn("UserArchetype not found with id {}", userArchetypeId);
                            return new UserArchetypeNotFoundException(userArchetypeId);
                        }
                );
    }
}
