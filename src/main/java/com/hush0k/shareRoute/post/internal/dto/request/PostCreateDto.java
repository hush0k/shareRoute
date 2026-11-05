package com.hush0k.shareRoute.post.internal.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PostCreateDto(

        @NotNull
        Integer cityId,

        @NotNull
        Integer countryId,

        @Size(max=2000)
        String content,

        @Size(max=10)
        List<@Size(max=2048) String> imgUrls

) {
    public PostCreateDto {
        imgUrls = imgUrls == null ? List.of() : imgUrls;
    }
}
