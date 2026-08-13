package com.hush0k.shareRoute.followers.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class UserAlreadyFollowUserException extends MessageKeyException {
    public UserAlreadyFollowUserException(UUID followerId, UUID followingId) {
        super("followers.alreadyExists", followerId, followingId);
    }
}
