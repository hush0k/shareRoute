package com.hush0k.shareRoute.followers.internal.dto.response;

import java.time.Instant;
import java.util.UUID;

public record FollowersResponseDto(
        UUID id,
        UUID followerId,
        UUID followingId,
        Instant createdAt
) {}
