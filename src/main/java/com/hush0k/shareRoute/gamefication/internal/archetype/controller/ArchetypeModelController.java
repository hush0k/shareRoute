package com.hush0k.shareRoute.gamefication.internal.archetype.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeModelResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.service.ArchetypeModelService;
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
public class ArchetypeModelController {

    private final ArchetypeModelService archetypeModelService;

    @PostMapping
    @Operation(summary = "Create an archetype model")
    public ResponseEntity<ArchetypeModelResponse> createArchetypeModel(@Valid @RequestBody ArchetypeModelCreate dto) {
        ArchetypeModelResponse created = archetypeModelService.createArchetypeModel(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{archetypeId}")
    @Operation(summary = "Update an archetype model")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeModelResponse> updateArchetypeModel(
            @PathVariable Long archetypeId,
            @Valid @RequestBody ArchetypeModelUpdateDto dto) {
        return ResponseEntity.ok(archetypeModelService.updateArchetypeModel(dto, archetypeId));
    }

    @DeleteMapping("/{archetypeId}")
    @Operation(summary = "Delete an archetype model")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteArchetypeModel(@PathVariable Long archetypeId) {
        archetypeModelService.deleteArchetypeModel(archetypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{archetypeId}")
    @Operation(summary = "Get an archetype model by id")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeModelResponse> getArchetypeModel(@PathVariable Long archetypeId) {
        return ResponseEntity.ok(archetypeModelService.getArchetypeModelById(archetypeId));
    }

    @GetMapping
    @Operation(summary = "Get all archetype models")
    public ResponseEntity<PageResponse<ArchetypeModelResponse>> getArchetypeModels(Pageable pageable) {
        return ResponseEntity.ok(archetypeModelService.getArchetypeModels(pageable));
    }

    @PatchMapping("/{archetypeId}/publish")
    @Operation(summary = "Change state of archetype to Publish")
    @ApiResponse(responseCode = "404", description = "Archetype not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ArchetypeModelResponse> publishArchetypeModel(@PathVariable Long archetypeId){
        return ResponseEntity.ok(archetypeModelService.publish(archetypeId));
    }
}
