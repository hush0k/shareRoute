package com.hush0k.shareRoute.gamefication.internal.achievement.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementTranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.service.AchievementService;
import com.hush0k.shareRoute.gamefication.internal.achievement.service.AchievementTranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/achievements/{achievementId}/translations")
@RequiredArgsConstructor
@Tag(name = "Achievement translations", description = "Translations of achievement models")
public class AchievementTranslationController {

    private final AchievementTranslationService achievementTranslationService;
    private final AchievementService achievementService;

    @PostMapping
    @Operation(summary = "Create a translation for an achievement")
    @ApiResponse(responseCode = "404", description = "Achievement not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementTranslationResponseDto> createAchievementTranslation(
            @PathVariable UUID achievementId,
            @Valid @RequestBody TranslationCreateRequest request) {
        Achievement achievement = achievementService.getAchievement(achievementId);

        AchievementTranslationCreateDto dto = AchievementTranslationCreateDto.builder()
                .achievement(achievement)
                .languageCode(request.languageCode())
                .name(request.name())
                .description(request.description())
                .build();

        AchievementTranslationResponseDto created = achievementTranslationService.createAchievementTranslation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all translations of an achievement")
    public ResponseEntity<PageResponse<AchievementTranslationResponseDto>> getAchievementTranslations(
            @PathVariable UUID achievementId,
            Pageable pageable) {
        return ResponseEntity.ok(achievementTranslationService.getAchievementTranslations(achievementId, pageable));
    }

    @GetMapping("/{languageCode}")
    @Operation(summary = "Get a translation by language")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementTranslationResponseDto> getAchievementTranslation(
            @PathVariable UUID achievementId,
            @PathVariable String languageCode) {
        return ResponseEntity.ok(achievementTranslationService.getAchievementTranslation(achievementId, languageCode));
    }

    @PatchMapping("/{languageCode}")
    @Operation(summary = "Update a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementTranslationResponseDto> updateAchievementTranslation(
            @PathVariable UUID achievementId,
            @PathVariable String languageCode,
            @Valid @RequestBody AchievementTranslationUpdateDto dto) {
        return ResponseEntity.ok(achievementTranslationService.updateAchievementTranslation(achievementId, languageCode, dto));
    }

    @DeleteMapping("/{languageCode}")
    @Operation(summary = "Delete a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteAchievementTranslation(
            @PathVariable UUID achievementId,
            @PathVariable String languageCode) {
        achievementTranslationService.deleteAchievementTranslation(achievementId, languageCode);
        return ResponseEntity.noContent().build();
    }

    public record TranslationCreateRequest(
            @NotBlank
            @Size(min = 3, max = 100)
            String name,

            @Size(min = 3, max = 1000)
            String description,

            @NotBlank
            @Size(max = 10)
            String languageCode
    ) {}
}
