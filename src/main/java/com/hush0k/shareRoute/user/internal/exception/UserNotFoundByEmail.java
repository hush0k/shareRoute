package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserNotFoundByEmail extends MessageKeyException {
    public UserNotFoundByEmail(String email) {
        super("user.notFound.byEmail", email);
    }
}