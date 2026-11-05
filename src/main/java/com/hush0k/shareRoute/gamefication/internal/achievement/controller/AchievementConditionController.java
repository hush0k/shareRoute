package com.hush0k.shareRoute.gamefication.internal.achievement.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.request.AchievementConditionUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionResponseDto;
import com.hush0k.shareRoute.gamefication.internal.achievement.service.AchievementConditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievement-conditions")
@RequiredArgsConstructor
@Tag(name = "Achievement conditions", description = "Conditions required to earn an achievement")
public class AchievementConditionController {

    private final AchievementConditionService achievementConditionService;

    @PatchMapping("/{conditionId}")
    @Operation(summary = "Update an achievement condition")
    @ApiResponse(responseCode = "404", description = "Achievement condition not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AchievementConditionResponseDto> updateAchievementCondition(
            @PathVariable Long conditionId,
            @Valid @RequestBody AchievementConditionUpdateDto dto) {
        return ResponseEntity.ok(achievementConditionService.updateAchievementCondition(conditionId, dto));
    }
}
