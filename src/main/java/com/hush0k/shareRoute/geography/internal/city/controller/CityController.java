package com.hush0k.shareRoute.geography.internal.city.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.city.dto.response.CityResponseDto;
import com.hush0k.shareRoute.geography.internal.city.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "Cities", description = "Cities lookup")
public class CityController {

    private final CityService cityService;

    @GetMapping("/{cityId}")
    @Operation(summary = "Get a city by id")
    @ApiResponse(responseCode = "404", description = "City not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CityResponseDto> getCity(@PathVariable Integer cityId) {
        return ResponseEntity.ok(cityService.getCityById(cityId));
    }

    @GetMapping
    @Operation(summary = "Get all cities, optionally filtered by country")
    public ResponseEntity<PageResponse<CityResponseDto>> getCities(
            @RequestParam(required = false) Integer countryId,
            Pageable pageable) {
        return ResponseEntity.ok(cityService.getCities(countryId, pageable));
    }
}
