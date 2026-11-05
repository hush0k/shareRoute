package com.hush0k.shareRoute.gamefication.internal.achievement.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementFullResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
@Tag(name = "Achievements", description = "Achievement models")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    @Operation(summary = "Create an achievement")
    public ResponseEntity<AchievementResponseDto> createAchievement(@Valid @RequestBody AchievementCreateDto dto) {
        AchievementResponseDto created = achievementService.createAchievement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{achievementId}")
    @Operation(summary = "Update an achievement")
    @ApiResponse(responseCode = "404", description = "Achievement not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementResponseDto> updateAchievement(
            @PathVariable UUID achievementId,
            @Valid @RequestBody AchievementUpdateDto dto) {
        return ResponseEntity.ok(achievementService.updateAchievement(dto, achievementId));
    }

    @DeleteMapping("/{achievementId}")
    @Operation(summary = "Delete an achievement")
    @ApiResponse(responseCode = "404", description = "Achievement not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteAchievement(@PathVariable UUID achievementId) {
        achievementService.deleteAchievement(achievementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{achievementId}")
    @Operation(summary = "Get an achievement by id")
    @ApiResponse(responseCode = "404", description = "Achievement not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementFullResponseDto> getAchievement(
            @PathVariable UUID achievementId,
            Locale locale) {
        return ResponseEntity.ok(achievementService.getAchievementById(achievementId, locale.getLanguage()));
    }

    @GetMapping
    @Operation(summary = "Get achievements")
    public ResponseEntity<PageResponse<AchievementResponseDto>> getAchievements(
            Pageable pageable,
            Locale locale) {
        return ResponseEntity.ok(achievementService.getAchievements(pageable, locale.getLanguage()));
    }
}
