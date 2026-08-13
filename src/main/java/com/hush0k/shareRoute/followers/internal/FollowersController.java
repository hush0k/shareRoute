package com.hush0k.shareRoute.followers.internal;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.followers.internal.dto.request.FollowersCreateDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersListDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersResponseDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowingListDto;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
@Tag(name = "Followers", description = "Follow relationships between users")
public class FollowersController {

    private final FollowersService followersService;

    @PostMapping
    @Operation(summary = "Follow a user")
    @ApiResponse(responseCode = "409", description = "User is already followed",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<FollowersResponseDto> followUser(@Valid @RequestBody FollowersCreateDto dto) {
        FollowersResponseDto created = followersService.followUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{followingId}")
    @Operation(summary = "Get a user's followers",
            description = "Returns the user's followers, flagging whether currentUserId follows each of them")
    public ResponseEntity<FollowersListDto> getFollowers(
            @PathVariable UUID followingId,
            @RequestParam UUID currentUserId,
            Pageable pageable) {
        return ResponseEntity.ok(followersService.getFollowersList(followingId, currentUserId, pageable));
    }

    @GetMapping("/{followerId}/following")
    @Operation(summary = "Get the users a user is following")
    public ResponseEntity<FollowingListDto> getFollowing(
            @PathVariable UUID followerId,
            Pageable pageable) {
        return ResponseEntity.ok(followersService.getFollowingList(followerId, pageable));
    }
}