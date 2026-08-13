package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class TooManyLoginAttemptsException extends MessageKeyException {
    public TooManyLoginAttemptsException() {
        super("auth.tooMany.login.attempts");
    }
}
