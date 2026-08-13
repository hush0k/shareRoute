package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserWithEmailAlreadyExists extends MessageKeyException {
    public UserWithEmailAlreadyExists(String email) {
        super("auth.email.alreadyExists", email);
    }
}
