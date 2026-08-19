package com.hush0k.shareRoute.gamefication.internal.rank.mapper;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import com.hush0k.shareRoute.gamefication.internal.rank.domain.RankTranslation;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankResponseDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankTranslationResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RankTranslationMapper {
    RankTranslation toRankTranslation(RankTranslationCreateDto dto);
    RankTranslationResponseDto toRankTranslationResponse(RankTranslation rankTranslation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRank(RankTranslationUpdateDto dto, @MappingTarget RankTranslation rankTranslation);
}
