package com.hush0k.shareRoute.gamefication.internal.archetype.mapper;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArchetypeMapper {

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Archetype toArchetype(ArchetypeCreate dto);

    ArchetypeResponse toArchetypeResponse(Archetype dto);
    Archetype toArchetypeFromResponse(ArchetypeResponse dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArchetype(ArchetypeUpdateDto dto, @MappingTarget Archetype archetype);

}
