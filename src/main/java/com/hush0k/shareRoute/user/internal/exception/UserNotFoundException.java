package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class UserNotFoundException extends MessageKeyException {
    public UserNotFoundException(UUID id) {
        super("user.notFound.byId", id);
    }
}