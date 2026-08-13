package com.hush0k.shareRoute.followers.internal.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FollowersCreateDto (

        @NotNull(message = "{followers.id.notNull}")
        UUID followerId,

        @NotNull(message = "{followers.id.notNull}")
        UUID followingId

){}
