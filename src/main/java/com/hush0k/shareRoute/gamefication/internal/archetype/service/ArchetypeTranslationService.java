package com.hush0k.shareRoute.gamefication.internal.archetype.service;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.TranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.TranslationNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.TranslationMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.ArchetypeTranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArchetypeTranslationService {

    private final ArchetypeTranslationRepository translationRepository;
    private final TranslationMapper  translationMapper;

    public void createArchetypeTranslation(TranslationCreateDto dto) {
        translationRepository.save(translationMapper.toArchetypeTranslation(dto));
    }

    public TranslationResponseDto updateArchetypeTranslation(TranslationUpdateDto dto, Long archetypeId, String languageCode) {
        ArchetypeTranslation archetypeTranslation = getArchetypeTranslationEntry(archetypeId, languageCode);
        translationMapper.updateTranslation(dto, archetypeTranslation);
        return translationMapper.toTranslationResponseDto(archetypeTranslation);
    }

    public void deleteArchetypeTranslation(Long archetypeId, String languageCode) {
        ArchetypeTranslation archetypeTranslation = getArchetypeTranslationEntry(archetypeId, languageCode);
        translationRepository.delete(archetypeTranslation);
    }

    @Transactional(readOnly = true)
    public TranslationResponseDto getArchetypeTranslation(Long archetypeId, String languageCode) {
        ArchetypeTranslation archetypeTranslation = getArchetypeTranslationEntry(archetypeId, languageCode);
        return translationMapper.toTranslationResponseDto(archetypeTranslation);
    }

    @Transactional(readOnly = true)
    public PageResponse<TranslationResponseDto> getArchetypeTranslationsByLanguage(Pageable pageable, String language) {
        Page<ArchetypeTranslation> translations = translationRepository.findAllByLanguageCode(language, pageable);
        Page<TranslationResponseDto> mapped = translations.map(translationMapper::toTranslationResponseDto);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public PageResponse<TranslationResponseDto> getArchetypeTranslationsByArchetypeId(Long archetypeId, Pageable pageable) {
        Page<ArchetypeTranslation> translations = translationRepository.findAllByArchetypeId(archetypeId, pageable);
        Page<TranslationResponseDto> mapped = translations.map(translationMapper::toTranslationResponseDto);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public List<ArchetypeTranslation> getTranslationsForArchetypes(List<Long> archetypeIds,
                                                                                   String language) {
        return translationRepository.findAllByArchetypeIdInAndLanguageCode(archetypeIds, language);
    }

    private ArchetypeTranslation getArchetypeTranslationEntry(Long archetypeId, String languageCode) {
        return translationRepository.findByArchetypeIdAndLanguageCode(archetypeId, languageCode)
                .orElseThrow(
                        () -> {
                            log.warn("Could not find translation for archetype {} and language {}", archetypeId, languageCode);
                            return new TranslationNotFoundException(archetypeId, languageCode);
                        }
                );
    }
}
