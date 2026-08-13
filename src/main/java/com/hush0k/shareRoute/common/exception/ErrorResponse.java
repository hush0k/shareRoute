package com.hush0k.shareRoute.common.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        int status,
        String code,
        String message,
        Instant timestamp,
        String path,
        List<String> details
) {}