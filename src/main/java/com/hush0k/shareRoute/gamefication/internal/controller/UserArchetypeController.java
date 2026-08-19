package com.hush0k.shareRoute.gamefication.internal.controller;

import com.hush0k.shareRoute.auth.CurrentUser;
import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeCreateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.UserArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.response.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.dto.response.UserArchetypeResponseDto;
import com.hush0k.shareRoute.gamefication.internal.exceptions.ArchetypeModelNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.repository.ArchetypeRepository;
import com.hush0k.shareRoute.gamefication.internal.service.UserArchetypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-archetypes")
@RequiredArgsConstructor
@Tag(name = "User archetypes", description = "Archetypes unlocked/discovered by a user")
public class UserArchetypeController {

    private final UserArchetypeService userArchetypeService;
    private final ArchetypeRepository archetypeRepository;

    @PostMapping("/{archetypeId}")
    @Operation(summary = "Create a user archetype")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UserArchetypeResponseDto> createUserArchetype(
            @CurrentUser UUID userId,
            @PathVariable Long archetypeId,
            @Valid @RequestBody UserArchetypeCreateDto dto) {
        UserArchetypeResponseDto created = userArchetypeService.createUserArchetype(dto, userId, archetypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{userArchetypeId}")
    @Operation(summary = "Update a user archetype")
    @ApiResponse(responseCode = "404", description = "User archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UserArchetypeResponseDto> updateUserArchetype(
            @PathVariable Long userArchetypeId,
            @Valid @RequestBody UserArchetypeUpdateDto dto) {
        return ResponseEntity.ok(userArchetypeService.updateUserArchetype(dto, userArchetypeId));
    }

    @DeleteMapping("/{userArchetypeId}")
    @Operation(summary = "Delete a user archetype")
    @ApiResponse(responseCode = "404", description = "User archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteUserArchetype(@PathVariable Long userArchetypeId) {
        userArchetypeService.deleteUserArchetype(userArchetypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userArchetypeId}")
    @Operation(summary = "Get a user archetype by id")
    @ApiResponse(responseCode = "404", description = "User archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UserArchetypeResponseDto> getUserArchetype(@PathVariable Long userArchetypeId) {
        return ResponseEntity.ok(userArchetypeService.getUserArchetypeById(userArchetypeId));
    }

    @GetMapping
    @Operation(summary = "Get user archetypes by user id")
    public ResponseEntity<PageResponse<UserArchetypeResponseDto>> getUserArchetypes(
            @RequestParam UUID userId,
            Pageable pageable) {
        return ResponseEntity.ok(userArchetypeService.getUserArchetypesByUserId(pageable, userId));
    }
}
