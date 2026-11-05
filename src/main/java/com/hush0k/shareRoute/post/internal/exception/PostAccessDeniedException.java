package com.hush0k.shareRoute.post.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class PostAccessDeniedException extends MessageKeyException {
    public PostAccessDeniedException(UUID postId) {
        super("post.accessDenied", postId);
    }
}
