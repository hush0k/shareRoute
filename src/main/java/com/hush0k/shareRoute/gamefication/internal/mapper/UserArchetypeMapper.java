package com.hush0k.shareRoute.gamefication.internal.mapper;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeTranslationModel;
import com.hush0k.shareRoute.gamefication.internal.domain.UserArchetype;
import com.hush0k.shareRoute.gamefication.internal.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeCreateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.response.UserArchetypeResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserArchetypeMapper {
    UserArchetype toUserArchetype(UserArchetypeCreateDto dto);

    @Mapping(source = "archetype", target = "archetypeModel")
    UserArchetypeResponseDto toUserArchetypeResponseDto(UserArchetype userArchetype);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserArchetype(UserArchetypeUpdateDto dto, @MappingTarget UserArchetype userArchetype);
}
