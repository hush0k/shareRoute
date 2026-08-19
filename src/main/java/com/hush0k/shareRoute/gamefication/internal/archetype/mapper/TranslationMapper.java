package com.hush0k.shareRoute.gamefication.internal.archetype.mapper;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.TranslationResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TranslationMapper {
    @Mapping(source = "archetype", target = "archetype")
    ArchetypeTranslation toArchetypeTranslation(TranslationCreateDto dto);

    @Mapping(source = "archetype", target = "archetype")
    TranslationResponseDto toTranslationResponseDto(ArchetypeTranslation archetypeTranslation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTranslation(TranslationUpdateDto dto, @MappingTarget ArchetypeTranslation translation);
}
