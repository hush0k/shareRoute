package com.hush0k.shareRoute.auth.internal.dto.request;

import com.hush0k.shareRoute.auth.internal.annotations.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordMatches
public record RegisterDataDto(

        @Email
        @NotBlank
        String email,

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9\\s]).{8,72}$",
                message = "auth.password.invalid")
        String password,

        String confirmPassword
) {}
