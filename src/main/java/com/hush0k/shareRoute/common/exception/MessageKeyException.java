package com.hush0k.shareRoute.common.exception;

import lombok.Getter;

@Getter
public abstract class MessageKeyException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    protected MessageKeyException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}