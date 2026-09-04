package com.hush0k.shareRoute.gamefication.internal.archetype.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.service.ArchetypeService;
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
@RequestMapping("/api/archetypes")
@RequiredArgsConstructor
@Tag(name = "Archetypes", description = "Archetype models management")
public class ArchetypeController {

    private final ArchetypeService archetypeService;

    @PostMapping
    @Operation(summary = "Create an archetype model")
    public ResponseEntity<ArchetypeResponse> createArchetype(@Valid @RequestBody ArchetypeCreate dto) {
        ArchetypeResponse created = archetypeService.createArchetype(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{archetypeId}")
    @Operation(summary = "Update an archetype model")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeResponse> updateArchetype(
            @PathVariable Long archetypeId,
            @Valid @RequestBody ArchetypeUpdateDto dto) {
        return ResponseEntity.ok(archetypeService.updateArchetype(dto, archetypeId));
    }

    @DeleteMapping("/{archetypeId}")
    @Operation(summary = "Delete an archetype model")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteArchetype(@PathVariable Long archetypeId) {
        archetypeService.deleteArchetype(archetypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{archetypeId}")
    @Operation(summary = "Get an archetype model by id")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeResponse> getArchetype(@PathVariable Long archetypeId) {
        return ResponseEntity.ok(archetypeService.getArchetypeById(archetypeId));
    }

    @GetMapping
    @Operation(summary = "Get all archetype models")
    public ResponseEntity<PageResponse<ArchetypeResponse>> getArchetypes(Pageable pageable) {
        return ResponseEntity.ok(archetypeService.getArchetypes(pageable));
    }

    @PatchMapping("/{archetypeId}/publish")
    @Operation(summary = "Change state of archetype to Publish")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeResponse> publishArchetype(@PathVariable Long archetypeId){
        return ResponseEntity.ok(archetypeService.publishArchetype(archetypeId));
    }
}
