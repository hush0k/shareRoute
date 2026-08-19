package com.hush0k.shareRoute.gamefication.internal.archetype.service;

import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.ArchetypeNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.ArchetypeMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.ArchetypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArchetypeService {

    private final ArchetypeRepository archetypeRepository;
    private final ArchetypeMapper archetypeMapper;
    private final ArchetypeTranslationService archetypeTranslationService;

    private static final String DEFAULT_LANGUAGE_CODE = "en";


    public ArchetypeResponse createArchetype(ArchetypeCreate dto){
        Archetype newArchetype = archetypeRepository.save(archetypeMapper.toArchetype(dto));

        TranslationCreateDto translationCreateDto = TranslationCreateDto.builder()
                .archetype(newArchetype)
                .description(dto.engDescription())
                .name(dto.engName())
                .languageCode(DEFAULT_LANGUAGE_CODE)
                .build();
        archetypeTranslationService.createTranslation(translationCreateDto);

        return archetypeMapper.toArchetypeResponse(newArchetype);
    }

    public ArchetypeResponse updateArchetype(ArchetypeUpdateDto dto, Long archetypeId){
        Archetype archetype = getArchetypeOrThrow(archetypeId);

        archetypeMapper.updateArchetype(dto, archetype);
        Archetype updatedArchetype = archetypeRepository.save(archetype);

        return archetypeMapper.toArchetypeResponse(updatedArchetype);
    }

    public void deleteArchetype(Long archetypeId){
        Archetype archetype = getArchetypeOrThrow(archetypeId);
        archetypeRepository.delete(archetype);
    }

    @Transactional(readOnly = true)
    public ArchetypeResponse getArchetypeById(Long archetypeId){
        Archetype archetype = getArchetypeOrThrow(archetypeId);
        return archetypeMapper.toArchetypeResponse(archetype);
    }

    @Transactional(readOnly = true)
    public PageResponse<ArchetypeResponse> getArchetypes(Pageable pageable){
        Page<Archetype> archetypes = archetypeRepository.findAll(pageable);
        Page<ArchetypeResponse> mapped = archetypes.map(archetypeMapper::toArchetypeResponse);
        return PageResponse.of(mapped);
    }

    public ArchetypeResponse publish(Long archetypeId){
        Archetype archetype = getArchetypeOrThrow(archetypeId);
        archetype.setArchetypeState(ArchetypeState.PUBLISHED);
        return archetypeMapper.toArchetypeResponse(archetypeRepository.save(archetype));
    }

    private Archetype getArchetypeOrThrow(Long archetypeId){
        return archetypeRepository.findById(archetypeId).orElseThrow(
                () -> {
                    log.warn("Archetype with id {} not found", archetypeId);
                    return new ArchetypeNotFoundException(archetypeId);
                }
        );
    }

}
