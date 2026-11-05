package com.hush0k.shareRoute.followers.internal.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record FollowersResponseDto(
        UUID id,
        UUID followerId,
        UUID followingId,
        Instant createdAt
) {}
