package com.hush0k.shareRoute.user.internal.service;

import com.hush0k.shareRoute.user.internal.UserMapper;
import com.hush0k.shareRoute.user.internal.domain.UserModel;
import com.hush0k.shareRoute.user.internal.dto.request.UserBootstrapDto;
import com.hush0k.shareRoute.user.internal.dto.request.UserCreateDto;
import com.hush0k.shareRoute.user.internal.dto.request.UserUpdateDto;
import com.hush0k.shareRoute.user.internal.dto.response.PageResponse;
import com.hush0k.shareRoute.user.internal.dto.response.UserResponseDto;
import com.hush0k.shareRoute.user.internal.exception.UserNotFoundByEmail;
import com.hush0k.shareRoute.user.internal.exception.UserNotFoundByNicknameException;
import com.hush0k.shareRoute.user.internal.exception.UserNotFoundException;
import com.hush0k.shareRoute.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public PageResponse<UserResponseDto> getUsers(Pageable pageable){
        Page<UserModel> users = userRepository.findAll(pageable);
        Page<UserResponseDto> mapped = users.map(userMapper::toUserResponseDto);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public UserModel getUserById(UUID id){
        return userRepository.findById(id).orElseThrow(
                () -> {
                   log.warn("User with id {} not found", id);
                    return new UserNotFoundException(id);
                }
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email){
        UserModel user = userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.warn("User with email {} not found", email);
                    return new UserNotFoundByEmail(email);
                }
        );
        return userMapper.toUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserModel> getUserByEmailWithoutException(String email){
        UserModel user = userRepository.findByEmail(email).orElse(null);
        return Optional.ofNullable(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByNickname(String nickname){
        UserModel user = userRepository.findByNickname(nickname).orElseThrow(
                () -> {
                    log.warn("User with name {} not found", nickname);
                    return new UserNotFoundByNicknameException(nickname);
                }
        );
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto createUser(UserCreateDto dto){
        UserModel user = userMapper.toUserModel(dto);
        UserModel createdUser = userRepository.save(user);

        return userMapper.toUserResponseDto(createdUser);
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void createBootstrapUser(UserBootstrapDto dto) {
        log.info("createBootstrapUser: id={}, email={}, nickname={}, firstName={}, lastName={}",
                dto.id(), dto.email(), dto.nickname(), dto.firstName(), dto.lastName());

        UserModel user = new UserModel();
        user.setId(dto.id());
        user.setEmail(dto.email());
        user.setNickname(dto.nickname());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setCompleteRegistration(false);
        user.setFirstEnter(true);

        UserModel createdUser = userRepository.saveAndFlush(user);
        log.info("createBootstrapUser: saved & flushed id={}", createdUser.getId());
        userMapper.toUserResponseDto(createdUser);
    }

    public UserResponseDto updateUser(UUID id, UserUpdateDto dto){
        UserModel user = getUserById(id);
        userMapper.updateUserFromDto(dto, user);
        UserModel updatedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(updatedUser);
    }

    public void deleteUser(UUID id){
        UserModel user = getUserById(id);
        userRepository.delete(user);
    }

}
