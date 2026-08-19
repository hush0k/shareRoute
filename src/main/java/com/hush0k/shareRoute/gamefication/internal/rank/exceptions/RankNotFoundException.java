package com.hush0k.shareRoute.gamefication.internal.rank.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class RankNotFoundException extends MessageKeyException {
    public RankNotFoundException(Long rankId) {
        super("rank.notFound", rankId);
    }
}