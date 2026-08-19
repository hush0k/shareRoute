package com.hush0k.shareRoute.gamefication.internal.service;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.domain.UserArchetype;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeCreateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.response.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.dto.response.UserArchetypeResponseDto;
import com.hush0k.shareRoute.gamefication.internal.enums.ArchetypeState;
import com.hush0k.shareRoute.gamefication.internal.exceptions.NotPublishedArchetypeException;
import com.hush0k.shareRoute.gamefication.internal.exceptions.UserArchetypeNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.mapper.ArchetypeModelMapper;
import com.hush0k.shareRoute.gamefication.internal.mapper.UserArchetypeMapper;
import com.hush0k.shareRoute.gamefication.internal.repository.UserArchetypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserArchetypeService {

    private final UserArchetypeRepository userArchetypeRepository;
    private final UserArchetypeMapper userArchetypeMapper;
    private final ArchetypeModelService archetypeModelService;
    private final ArchetypeModelMapper archetypeModelMapper;

    public UserArchetypeResponseDto createUserArchetype(UserArchetypeCreateDto dto, UUID userId, Long archetypeId){
        ArchetypeModel archetypeModel = archetypeModelMapper.toArchetypeFromResponse(archetypeModelService.getArchetypeModelById(archetypeId));
        UserArchetype newUserArchetype = userArchetypeMapper.toUserArchetype(dto);
        if (archetypeModel.getArchetypeState() != ArchetypeState.PUBLISHED) {
            log.warn("Attempt to assign not published archetype with id {}", archetypeModel.getId());
            throw new NotPublishedArchetypeException(archetypeModel.getId());
        }
        newUserArchetype.setUserId(userId);
        newUserArchetype.setArchetype(archetypeModel);
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
    public PageResponse<UserArchetypeResponseDto> getUserArchetypesByUserId(Pageable pageable, UUID userId){
        Page<UserArchetype> userArchetypes = userArchetypeRepository.findByUserId(userId, pageable);
        Page<UserArchetypeResponseDto> mapped = userArchetypes.map(userArchetypeMapper::toUserArchetypeResponseDto);
        return PageResponse.of(mapped);
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
