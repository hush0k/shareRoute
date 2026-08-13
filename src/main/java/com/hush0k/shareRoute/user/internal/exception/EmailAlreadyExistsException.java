package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class EmailAlreadyExistsException extends MessageKeyException {
    public EmailAlreadyExistsException(String email) {
        super("user.email.alreadyExists", email);
    }
}