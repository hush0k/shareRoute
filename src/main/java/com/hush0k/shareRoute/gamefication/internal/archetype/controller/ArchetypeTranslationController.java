package com.hush0k.shareRoute.gamefication.internal.archetype.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.TranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.ArchetypeModelNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.ArchetypeRepository;
import com.hush0k.shareRoute.gamefication.internal.archetype.service.ArchetypeTranslationService;
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
@RequestMapping("/api/translations")
@RequiredArgsConstructor
@Tag(name = "Archetype translations", description = "Translations of archetype models")
public class ArchetypeTranslationController {

    private final ArchetypeTranslationService archetypeTranslationService;
    private final ArchetypeRepository archetypeRepository;

    @PostMapping("/api/archetypes/{archetypeId}/translations")
    @Operation(summary = "Create a translation for an archetype model")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> createTranslation(
            @PathVariable Long archetypeId,
            @Valid @RequestBody TranslationCreateRequest request) {
        ArchetypeModel archetypeModel = archetypeRepository.findById(archetypeId)
                .orElseThrow(() -> new ArchetypeModelNotFoundException(archetypeId));

        TranslationCreateDto dto = TranslationCreateDto.builder()
                .archetypeModel(archetypeModel)
                .name(request.name())
                .description(request.description())
                .languageCode(request.languageCode())
                .build();

        archetypeTranslationService.createTranslation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{translationId}")
    @Operation(summary = "Update a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<TranslationResponseDto> updateTranslation(
            @PathVariable Long translationId,
            @Valid @RequestBody TranslationUpdateDto dto) {
        return ResponseEntity.ok(archetypeTranslationService.updateTranslation(dto, translationId));
    }

    @DeleteMapping("/{translationId}")
    @Operation(summary = "Delete a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteTranslation(
            @PathVariable Long translationId) {
        archetypeTranslationService.deleteTranslation(translationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{translationId}")
    @Operation(summary = "Get a translation by id")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<TranslationResponseDto> getTranslation(
            @PathVariable Long translationId) {
        return ResponseEntity.ok(archetypeTranslationService.getTranslationById(translationId));
    }

    @GetMapping("/by-language")
    @Operation(summary = "Get translations by language")
    public ResponseEntity<PageResponse<TranslationResponseDto>> getTranslations(
            @RequestParam String language,
            Pageable pageable) {
        return ResponseEntity.ok(archetypeTranslationService.getArchetypeTranslationsByLanguage(pageable, language));
    }

    @GetMapping("/by-archetype-id/{archetypeId}")
    @Operation(summary = "Get translations by archetype id")
    public ResponseEntity<PageResponse<TranslationResponseDto>> getTranslationsByArchetype(
            @PathVariable Long archetypeId,
            Pageable pageable) {
        return ResponseEntity.ok(archetypeTranslationService.getArchetypeTranslationsByArchetypeId(archetypeId,
                pageable));
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
