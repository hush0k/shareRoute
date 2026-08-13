package com.hush0k.shareRoute.auth.internal.controller;

import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import com.hush0k.shareRoute.auth.internal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDataDto dto, Locale locale) {
        authService.register(dto, locale.getLanguage());
        return ResponseEntity.ok().build();
    }

}
