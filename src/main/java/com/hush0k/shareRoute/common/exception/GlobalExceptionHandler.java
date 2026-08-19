package com.hush0k.shareRoute.common.exception;

import com.hush0k.shareRoute.auth.internal.exception.NotMatchPasswordsException;
import com.hush0k.shareRoute.auth.internal.exception.UserWithEmailAlreadyExists;
import com.hush0k.shareRoute.followers.internal.exception.UserAlreadyFollowUserException;
import com.hush0k.shareRoute.followers.internal.exception.UserFollowThemselvesException;
import com.hush0k.shareRoute.gamefication.internal.exceptions.NotPublishedArchetypeException;
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