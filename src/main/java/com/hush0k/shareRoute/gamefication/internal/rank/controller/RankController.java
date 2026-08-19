package com.hush0k.shareRoute.gamefication.internal.rank.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankResponseDto;
import com.hush0k.shareRoute.gamefication.internal.rank.service.RankService;
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

@RestController
@RequestMapping("/api/ranks")
@RequiredArgsConstructor
@Tag(name = "Ranks", description = "Rank models management")
public class RankController {

    private final RankService rankService;

    @PostMapping
    @Operation(summary = "Create a rank")
    public ResponseEntity<RankResponseDto> createRank(@Valid @RequestBody RankCreateDto dto) {
        RankResponseDto created = rankService.createRank(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{rankId}")
    @Operation(summary = "Update a rank")
    @ApiResponse(responseCode = "404", description = "Rank not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RankResponseDto> updateRank(
            @PathVariable Long rankId,
            @Valid @RequestBody RankUpdateDto dto) {
        return ResponseEntity.ok(rankService.updateRank(dto, rankId));
    }

    @DeleteMapping("/{rankId}")
    @Operation(summary = "Delete a rank")
    @ApiResponse(responseCode = "404", description = "Rank not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteRank(@PathVariable Long rankId) {
        rankService.deleteRank(rankId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{rankId}")
    @Operation(summary = "Get a rank by id")
    @ApiResponse(responseCode = "404", description = "Rank not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RankResponseDto> getRank(@PathVariable Long rankId) {
        return ResponseEntity.ok(rankService.getRankById(rankId));
    }

    @GetMapping
    @Operation(summary = "Get all ranks")
    public ResponseEntity<PageResponse<RankResponseDto>> getRanks(Pageable pageable) {
        return ResponseEntity.ok(rankService.getRanks(pageable));
    }
}
