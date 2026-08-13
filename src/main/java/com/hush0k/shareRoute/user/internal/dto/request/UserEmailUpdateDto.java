package com.hush0k.shareRoute.user.internal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEmailUpdateDto(

        @Email(message = "{user.email.invalid}")
        @NotBlank(message = "{user.email.blank}")
        String email

) {}
