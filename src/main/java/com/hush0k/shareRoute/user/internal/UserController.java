package com.hush0k.shareRoute.user.internal;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.user.internal.domain.UserModel;
import com.hush0k.shareRoute.user.internal.dto.request.UserCreateDto;
import com.hush0k.shareRoute.user.internal.dto.request.UserUpdateDto;
import com.hush0k.shareRoute.user.internal.dto.response.PageResponse;
import com.hush0k.shareRoute.user.internal.dto.response.UserResponseDto;
import com.hush0k.shareRoute.user.internal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get list of users", description = "Returns a paginated list of users")
    public ResponseEntity<PageResponse<UserResponseDto>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserModel user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toUserResponseDto(user));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/nickname/{nickname}")
    @Operation(summary = "Get user by nickname")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDto> getUserByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponse(responseCode = "201", description = "User created")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        UserResponseDto created = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ApiResponse(responseCode = "204", description = "User deleted")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}