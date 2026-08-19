package com.hush0k.shareRoute.gamefication.internal.rank.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.request.RankTranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.rank.dto.response.RankTranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.rank.exceptions.RankNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.rank.repository.RankRepository;
import com.hush0k.shareRoute.gamefication.internal.rank.service.RankTranslationService;
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

@RestController
@RequestMapping("/api/ranks/{rankId}/translations")
@RequiredArgsConstructor
@Tag(name = "Rank translations", description = "Translations of rank models")
public class RankTranslationController {

    private final RankTranslationService rankTranslationService;
    private final RankRepository rankRepository;

    @PostMapping
    @Operation(summary = "Create a translation for a rank")
    @ApiResponse(responseCode = "404", description = "Rank not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RankTranslationResponseDto> createTranslation(
            @PathVariable Long rankId,
            @Valid @RequestBody TranslationCreateRequest request) {
        Rank rank = rankRepository.findById(rankId)
                .orElseThrow(() -> new RankNotFoundException(rankId));

        RankTranslationCreateDto dto = new RankTranslationCreateDto(
                rank,
                request.languageCode(),
                request.name(),
                request.description()
        );

        RankTranslationResponseDto created = rankTranslationService.createRank(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{languageCode}")
    @Operation(summary = "Update a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RankTranslationResponseDto> updateTranslation(
            @PathVariable Long rankId,
            @PathVariable String languageCode,
            @Valid @RequestBody RankTranslationUpdateDto dto) {
        return ResponseEntity.ok(rankTranslationService.updateRank(dto, rankId, languageCode));
    }

    @DeleteMapping("/{languageCode}")
    @Operation(summary = "Delete a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteTranslation(
            @PathVariable Long rankId,
            @PathVariable String languageCode) {
        rankTranslationService.deleteRank(rankId, languageCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{languageCode}")
    @Operation(summary = "Get a translation by rank id and language code")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RankTranslationResponseDto> getTranslation(
            @PathVariable Long rankId,
            @PathVariable String languageCode) {
        return ResponseEntity.ok(rankTranslationService.getRankTranslationByLangAndRankId(rankId, languageCode));
    }

    @GetMapping
    @Operation(summary = "Get translations by langueage")
    public ResponseEntity<PageResponse<RankTranslationResponseDto>> getTranslations(
            @RequestParam String language,
            Pageable pageable) {
        return ResponseEntity.ok(rankTranslationService.getRankTranslations(pageable, language));
    }

    public record TranslationCreateRequest(
            @NotBlank
            @Size(min = 3, max = 200)
            String name,

            @Size(min = 3, max = 2000)
            String description,

            @NotBlank
            String languageCode
    ) {}
}
