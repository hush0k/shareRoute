package com.hush0k.shareRoute.common.exception;

import com.hush0k.shareRoute.auth.internal.exception.InvalidCredentialsExceptions;
import com.hush0k.shareRoute.auth.internal.exception.NotMatchPasswordsException;
import com.hush0k.shareRoute.auth.internal.exception.TooManyLoginAttemptsException;
import com.hush0k.shareRoute.auth.internal.exception.UserWithEmailAlreadyExists;
import com.hush0k.shareRoute.auth.internal.exception.UserWithNicknameAlreadyExists;
import com.hush0k.shareRoute.followers.internal.exception.UserAlreadyFollowUserException;
import com.hush0k.shareRoute.followers.internal.exception.UserFollowThemselvesException;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementConditionNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.achievement.exceptions.AchievementTranslationNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.NotPublishedArchetypeException;
import com.hush0k.shareRoute.gamefication.internal.rank.exceptions.RankNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.rank.exceptions.RankTranslationNotFoundException;
import com.hush0k.shareRoute.geography.internal.city.exceptions.CityCountryMismatchException;
import com.hush0k.shareRoute.geography.internal.city.exceptions.CityNotFoundException;
import com.hush0k.shareRoute.geography.internal.cityTranslation.exceptions.CityTranslationNotFoundException;
import com.hush0k.shareRoute.geography.internal.country.exceptions.CountryNotFoundException;
import com.hush0k.shareRoute.geography.internal.countryTranslation.exceptions.CountryTranslationNotFoundException;
import com.hush0k.shareRoute.post.internal.exception.PostAccessDeniedException;
import com.hush0k.shareRoute.post.internal.exception.PostNotFoundException;
import com.hush0k.shareRoute.user.internal.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String resolve(MessageKeyException ex) {
        return messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), LocaleContextHolder.getLocale());
    }

    // --- общие / сквозные исключения ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Input validation error",
                Instant.now(),
                request.getRequestURI(),
                details
        ));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReference(PropertyReferenceException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_SORT_PROPERTY",
                "Invalid sort property: " + ex.getPropertyName(),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "ACCESS_DENIED",
                "Access denied",
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    // --- модульные исключения, ловим по отдельности ---

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(UserNotFoundByEmail.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundByEmail ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }


    @ExceptionHandler(UserNotFoundByNicknameException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundByNicknameException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "EMAIL_ALREADY_EXISTS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(NicknameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleNicknameExists(NicknameAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "NICKNAME_ALREADY_EXISTS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_PASSWORD",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(UserAlreadyFollowUserException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyFollowUser(UserAlreadyFollowUserException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "USER_ALREADY_FOLLOWED",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(UserFollowThemselvesException.class)
    public ResponseEntity<ErrorResponse> handleUserFollowThemselves(UserFollowThemselvesException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "USER_FOLLOW_THEMSELVES",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(UserWithEmailAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserFollowThemselves(UserWithEmailAlreadyExists ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "EMAIL_ALREADY_EXISTS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(UserWithNicknameAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserWithNicknameAlreadyExists(UserWithNicknameAlreadyExists ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "NICKNAME_ALREADY_EXISTS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(NotMatchPasswordsException.class)
    public ResponseEntity<ErrorResponse> handleUserFollowThemselves(NotMatchPasswordsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "PASSWORDS_DO_NOT_MATCH",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(InvalidCredentialsExceptions.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsExceptions ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIALS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(TooManyLoginAttemptsException.class)
    public ResponseEntity<ErrorResponse> handleTooManyLoginAttempts(TooManyLoginAttemptsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ErrorResponse(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                "TOO_MANY_LOGIN_ATTEMPTS",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(NotPublishedArchetypeException.class)
    public ResponseEntity<ErrorResponse> handleNotPublishedArchetype(NotPublishedArchetypeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "ARCHETYPE_NOT_PUBLISHED",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(RankNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRankNotFound(RankNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "RANK_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(RankTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRankTranslationNotFound(RankTranslationNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "RANK_TRANSLATION_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAchievementNotFound(AchievementNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ACHIEVEMENT_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(AchievementConditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAchievementConditionNotFound(AchievementConditionNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ACHIEVEMENT_CONDITION_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(AchievementTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAchievementTranslationNotFound(AchievementTranslationNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ACHIEVEMENT_TRANSLATION_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFound(CountryNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "COUNTRY_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(CountryTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryTranslationNotFound(CountryTranslationNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "COUNTRY_TRANSLATION_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(CityCountryMismatchException.class)
    public ResponseEntity<ErrorResponse> handleCityCountryMismatch(CityCountryMismatchException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "CITY_COUNTRY_MISMATCH",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFound(CityNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "CITY_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(CityTranslationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityTranslationNotFound(CityTranslationNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "CITY_TRANSLATION_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "POST_NOT_FOUND",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(PostAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePostAccessDenied(PostAccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "POST_ACCESS_DENIED",
                resolve(ex),
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }

    // --- fallback, самый последний ---

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                "Internal server error",
                Instant.now(),
                request.getRequestURI(),
                List.of()
        ));
    }
}