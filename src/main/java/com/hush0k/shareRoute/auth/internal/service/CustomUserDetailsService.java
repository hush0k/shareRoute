package com.hush0k.shareRoute.auth.internal.service;

import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import com.hush0k.shareRoute.auth.internal.repository.CredentialsRepository;
import com.hush0k.shareRoute.user.UserLookupPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;
    private final UserLookupPort userService;

    @Override
    public CustomUserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Credentials credentials = isEmail(identifier)
                ? findByEmail(identifier)
                : findByNickname(identifier);

        return new CustomUserDetails(credentials);
    }

    private boolean isEmail(String identifier) {
        return identifier.contains("@");
    }

    private Credentials findByEmail(String email) {
        return credentialsRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
    }

    private Credentials findByNickname(String nickname) {
        UUID userId;
        try {
            userId = userService.getUserIdByNickname(nickname);
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException("User not found with nickname: " + nickname);
        }
        return credentialsRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException("Credentials not found for nickname: " + nickname)
        );
    }

}