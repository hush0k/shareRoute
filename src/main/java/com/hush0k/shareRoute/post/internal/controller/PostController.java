package com.hush0k.shareRoute.post.internal.controller;

import com.hush0k.shareRoute.auth.CurrentUser;
import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.post.internal.dto.request.PostCreateDto;
import com.hush0k.shareRoute.post.internal.dto.request.PostUpdateDto;
import com.hush0k.shareRoute.post.internal.dto.response.PostResponseDto;
import com.hush0k.shareRoute.post.internal.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post model management")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "Create a post model")
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostCreateDto postCreateDto,
            @Parameter(hidden = true)@CurrentUser UUID userId
    ) {
        return ResponseEntity.ok(postService.createPost(postCreateDto, userId));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get a post by id")
    @ApiResponse(responseCode = "404", description = "Post not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable UUID postId
    ) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping
    @Operation(summary = "Get all posts")
    public ResponseEntity<PageResponse<PostResponseDto>> getPosts(
            Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "Update a post model")
    @ApiResponse(responseCode = "404", description = "Post not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Post belongs to another user",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable UUID postId,
            @Valid @RequestBody PostUpdateDto postUpdateDto,
            @Parameter(hidden = true)@CurrentUser UUID userId
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, postUpdateDto, userId));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Delete a post model")
    @ApiResponse(responseCode = "204", description = "Post deleted")
    @ApiResponse(responseCode = "404", description = "Post not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403", description = "Post belongs to another user",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID postId,
            @Parameter(hidden = true)@CurrentUser UUID userId
    ) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

}
