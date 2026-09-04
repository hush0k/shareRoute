package com.hush0k.shareRoute.gamefication.internal.rank.service;

import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.rank.domain.RankTranslation;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankTranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.rank.exceptions.RankTranslationNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.rank.mapper.RankTranslationMapper;
import com.hush0k.shareRoute.gamefication.internal.rank.repository.RankTranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RankTranslationService {

    private final RankTranslationRepository rankTranslationRepository;
    private final RankTranslationMapper rankTranslationMapper;

    public RankTranslationResponseDto createRankTranslation(RankTranslationCreateDto dto){
        RankTranslation rankTranslation = rankTranslationMapper.toRankTranslation(dto);
        return rankTranslationMapper.toRankTranslationResponse(rankTranslationRepository.save(rankTranslation));
    }

    public RankTranslationResponseDto updateRankTranslation(RankTranslationUpdateDto dto, Long rankId, String languageCode){
        RankTranslation rankTranslation = getTranslation(rankId, languageCode);
        rankTranslationMapper.updateRank(dto, rankTranslation);
        return rankTranslationMapper.toRankTranslationResponse(rankTranslationRepository.save(rankTranslation));
    }

    public void deleteRankTranslation(Long rankId, String languageCode){
        RankTranslation rankTranslation = getTranslation(rankId, languageCode);
        rankTranslationRepository.delete(rankTranslation);
    }

    @Transactional(readOnly = true)
    public RankTranslationResponseDto getRankTranslationByLangAndRankId(Long rankId, String languageCode){
        return rankTranslationMapper.toRankTranslationResponse(getTranslation(rankId, languageCode));
    }

    @Transactional(readOnly = true)
    public PageResponse<RankTranslationResponseDto> getRankTranslations(Pageable pageable, String languageCode){
        Page<RankTranslation> rankTranslations = rankTranslationRepository.findAllByLang(languageCode, pageable);
        Page<RankTranslationResponseDto> mapped = rankTranslations.map(rankTranslationMapper::toRankTranslationResponse);
        return PageResponse.of(mapped);
    }

    private RankTranslation getTranslation(Long rankId, String languageCode){
        return rankTranslationRepository.findByRankIdAndLang(rankId, languageCode)
                .orElseThrow(
                        () -> {
                            log.warn("Could not find rank translation for rank {} and language {}", rankId, languageCode);
                            return new RankTranslationNotFoundException(rankId, languageCode);
                        }
                );
    }
}
