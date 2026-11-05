package com.hush0k.shareRoute.post.internal.dto.response;

import com.hush0k.shareRoute.user.UserBriefDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostResponseDto(

        UUID id,
        UserBriefDto user,
        Integer countryId,
        Integer cityId,
        String content,
        List<String> imgUrls,
        int sharedCount,
        int exp,
        Instant createdAt,
        Instant updatedAt

) {}
