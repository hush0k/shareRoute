package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserNotFoundByEmail extends MessageKeyException {
    public UserNotFoundByEmail(String email) {
        super("auth.notFound.byEmail", email);
    }
}
