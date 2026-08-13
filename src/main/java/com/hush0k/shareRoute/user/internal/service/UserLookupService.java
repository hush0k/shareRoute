package com.hush0k.shareRoute.user.internal.service;

import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.UserLookupPort;
import com.hush0k.shareRoute.user.internal.UserMapper;
import com.hush0k.shareRoute.user.internal.domain.UserModel;
import com.hush0k.shareRoute.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLookupService implements UserLookupPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public List<UserBriefDto> findBriefByUserId(List<java.util.UUID> userId) {
        return userRepository.findAllById(userId).stream()
                .map(userMapper::toUserBriefDto)
                .toList();
    }

    @Override
    public UserModel getUserById(UUID  userId) {
        return userService.getUserById(userId);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userService.getUserByEmailWithoutException(email);
    }

    @Override
    public UserModel getUserByNickname(String email) {
        return userMapper.toUserModel(userService.getUserByNickname(email));
    }
}
