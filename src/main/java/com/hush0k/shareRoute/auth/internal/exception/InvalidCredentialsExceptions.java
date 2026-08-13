package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class InvalidCredentialsExceptions extends MessageKeyException {
    public InvalidCredentialsExceptions() {
        super("auth.invalid.credentials");
    }
}
