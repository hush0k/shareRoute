package com.hush0k.shareRoute.followers.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class UserFollowThemselvesException extends MessageKeyException {
    public UserFollowThemselvesException(UUID followerId) {
        super("followers.followerThemselves", followerId);
    }
}
