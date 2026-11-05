package com.hush0k.shareRoute.post.internal.dto.request;

import jakarta.validation.constraints.Size;

import java.awt.print.PrinterGraphics;
import java.util.UUID;

public record PostUpdateDto(

        Integer cityId,

        Integer countryId,

        @Size(max=2000)
        String content

) {}
