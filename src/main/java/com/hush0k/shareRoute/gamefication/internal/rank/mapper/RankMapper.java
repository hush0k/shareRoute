package com.hush0k.shareRoute.gamefication.internal.rank.mapper;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RankMapper {
    Rank toRank(RankCreateDto dto);
    RankResponseDto toResponseDto(Rank rank);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRank(RankUpdateDto dto, @MappingTarget Rank rank);
}
