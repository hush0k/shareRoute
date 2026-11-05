package com.hush0k.shareRoute.post.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class PostNotFoundException extends MessageKeyException {
    public PostNotFoundException(UUID postId) {
        super("post.notFound", postId);
    }
}
