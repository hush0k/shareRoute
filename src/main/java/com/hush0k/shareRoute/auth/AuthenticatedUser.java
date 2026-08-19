package com.hush0k.shareRoute.auth;

import com.hush0k.shareRoute.auth.internal.Role;

import java.util.UUID;

/**
 * Identity of the caller, resolved entirely from JWT claims — no database lookup.
 * <p>
 * This is the principal placed in {@link org.springframework.security.core.context.SecurityContextHolder}
 * by the JWT filter, and is deliberately independent from any persistence-backed user
 * representation, so that authentication stays self-contained if auth is later split into
 * its own service: any service holding the same signing key can verify a token and build
 * this record without calling back into auth's database.
 */
public record AuthenticatedUser(UUID userId, Role role) {
}