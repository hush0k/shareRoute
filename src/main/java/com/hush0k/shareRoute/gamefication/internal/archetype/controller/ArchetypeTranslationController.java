package com.hush0k.shareRoute.gamefication.internal.archetype.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.TranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.ArchetypeNotFoundException;
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
    public ResponseEntity<Void> createArchetypeTranslation(
            @PathVariable Long archetypeId,
            @Valid @RequestBody TranslationCreateRequest request) {
        Archetype archetype = archetypeRepository.findById(archetypeId)
                .orElseThrow(() -> new ArchetypeNotFoundException(archetypeId));

        TranslationCreateDto dto = TranslationCreateDto.builder()
                .archetype(archetype)
                .name(request.name())
                .description(request.description())
                .languageCode(request.languageCode())
                .build();

        archetypeTranslationService.createArchetypeTranslation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/api/archetypes/{archetypeId}/translations/{languageCode}")
    @Operation(summary = "Update a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<TranslationResponseDto> updateArchetypeTranslation(
            @PathVariable Long archetypeId,
            @PathVariable String languageCode,
            @Valid @RequestBody TranslationUpdateDto dto) {
        return ResponseEntity.ok(archetypeTranslationService.updateArchetypeTranslation(dto, archetypeId, languageCode));
    }

    @DeleteMapping("/api/archetypes/{archetypeId}/translations/{languageCode}")
    @Operation(summary = "Delete a translation")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteArchetypeTranslation(
            @PathVariable Long archetypeId,
            @PathVariable String languageCode) {
        archetypeTranslationService.deleteArchetypeTranslation(archetypeId, languageCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/archetypes/{archetypeId}/translations/{languageCode}")
    @Operation(summary = "Get a translation by archetype id and language code")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<TranslationResponseDto> getArchetypeTranslation(
            @PathVariable Long archetypeId,
            @PathVariable String languageCode) {
        return ResponseEntity.ok(archetypeTranslationService.getArchetypeTranslation(archetypeId, languageCode));
    }

    @GetMapping("/by-language")
    @Operation(summary = "Get translations by language")
    public ResponseEntity<PageResponse<TranslationResponseDto>> getArchetypeTranslations(
            @RequestParam String language,
            Pageable pageable) {
        return ResponseEntity.ok(archetypeTranslationService.getArchetypeTranslationsByLanguage(pageable, language));
    }

    @GetMapping("/by-archetype-id/{archetypeId}")
    @Operation(summary = "Get translations by archetype id")
    public ResponseEntity<PageResponse<TranslationResponseDto>> getArchetypeTranslationsByArchetype(
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
