package com.hush0k.shareRoute.auth.internal.controller;

import com.hush0k.shareRoute.auth.internal.dto.request.LoginDataDto;
import com.hush0k.shareRoute.auth.internal.dto.request.RefreshTokenRequestDto;
import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import com.hush0k.shareRoute.auth.internal.dto.response.TokenResponseDto;
import com.hush0k.shareRoute.auth.internal.service.AuthService;
import com.hush0k.shareRoute.auth.internal.util.IpAddressResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDataDto dto, Locale locale) {
        authService.register(dto, locale.getLanguage());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")

    public ResponseEntity<TokenResponseDto> login(
            @Valid @RequestBody LoginDataDto dto,
            Locale locale,
            HttpServletRequest request) {
        String ipAddress = IpAddressResolver.resolve(request);
        return ResponseEntity.ok(authService.login(dto, locale.getLanguage(), ipAddress));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(
            @Valid @RequestBody RefreshTokenRequestDto dto
    ){
        return ResponseEntity.ok(authService.getAccessTokenByRefreshToken(dto.refreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            @RequestBody RefreshTokenRequestDto dto) {

        String header = request.getHeader("Authorization");
        String accessToken = header.substring(7);

        authService.logout(accessToken, dto.refreshToken());
        return ResponseEntity.ok().build();
    }
}
