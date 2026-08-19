package com.hush0k.shareRoute.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Resolves the authenticated user for a controller method parameter.
 * <p>
 * Supports two parameter types:
 * <ul>
 *     <li>{@link java.util.UUID} — the authenticated user's id</li>
 *     <li>{@link AuthenticatedUser} — the full authenticated principal (userId + role)</li>
 * </ul>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}