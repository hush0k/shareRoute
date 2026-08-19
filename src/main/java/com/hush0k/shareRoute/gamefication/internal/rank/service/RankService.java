package com.hush0k.shareRoute.gamefication.internal.rank.service;

import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankResponseDto;
import com.hush0k.shareRoute.gamefication.internal.rank.exceptions.RankNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.rank.mapper.RankMapper;
import com.hush0k.shareRoute.gamefication.internal.rank.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RankService {

    private final RankRepository rankRepository;
    private final RankMapper rankMapper;

    public RankResponseDto createRank(RankCreateDto dto) {
        Rank rank = rankMapper.toRank(dto);
        return rankMapper.toResponseDto(rankRepository.save(rank));
    }

    public RankResponseDto updateRank(RankUpdateDto dto, Long rankId) {
        Rank rank = getRank(rankId);
        rankMapper.updateRank(dto, rank);
        return rankMapper.toResponseDto(rankRepository.save(rank));
    }

    public void deleteRank(Long rankId) {
        rankRepository.deleteById(rankId);
    }

    public RankResponseDto getRankById(Long rankId) {
        Rank rank = getRank(rankId);
        return rankMapper.toResponseDto(rank);
    }

    public PageResponse<RankResponseDto> getRanks(Pageable pageable) {
        Page<Rank> page = rankRepository.findAll(pageable);
        Page<RankResponseDto> mapped = page.map(rankMapper::toResponseDto);
        return PageResponse.of(mapped);
    }

    private Rank getRank(Long rankId) {
        return rankRepository.findById(rankId).orElseThrow(
                () -> {
                    log.warn("Could not find rank with id {}", rankId);
                    return new RankNotFoundException(rankId);
                }
        );
    }

}
