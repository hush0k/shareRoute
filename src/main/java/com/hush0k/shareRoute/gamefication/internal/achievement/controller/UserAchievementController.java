package com.hush0k.shareRoute.gamefication.internal.achievement.controller;

import com.hush0k.shareRoute.auth.CurrentUser;
import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.UserAchievementCreateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.UserAchievementResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.service.UserAchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-achievements")
@RequiredArgsConstructor
@Tag(name = "User achievements", description = "Achievements tracked/earned by a user")
public class UserAchievementController {

    private final UserAchievementService userAchievementService;

    @PostMapping("/{achievementId}")
    @Operation(summary = "Create a user achievement")
    @ApiResponse(responseCode = "404", description = "Achievement not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UserAchievementResponseDto> createUserAchievement(
            @Parameter(hidden = true) @CurrentUser UUID userId,
            @PathVariable UUID achievementId) {
        UserAchievementCreateDto dto = new UserAchievementCreateDto(userId, achievementId);
        UserAchievementResponseDto created = userAchievementService.createUserAchievement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get achievements of the current user")
    public ResponseEntity<PageResponse<UserAchievementResponseDto>> getUserAchievements(
            @Parameter(hidden = true) @CurrentUser UUID userId,
            Pageable pageable) {
        return ResponseEntity.ok(userAchievementService.getUserAchievements(userId, pageable));
    }
}
