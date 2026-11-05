package com.hush0k.shareRoute.followers.internal;

import com.hush0k.shareRoute.followers.internal.dto.request.FollowersCreateDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersResponseDto;
import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.UserLookupPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class FollowerServiceTest {

    @Mock FollowersRepository followersRepository;
    @Mock FollowersMapper followersMapper;
    @Mock UserLookupPort userService;

    @InjectMocks FollowersService followersService;

    @Test
    void follow_success_returnsResponse() {
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();
        FollowersCreateDto dto = new FollowersCreateDto(followerId, followingId);
        UserBriefDto follower = UserBriefDto.builder().id(followerId).build();
        UserBriefDto following = UserBriefDto.builder().id(followingId).build();
        Followers mapped = new Followers();
        Followers saved = new Followers();
        FollowersResponseDto response = FollowersResponseDto.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();

        when(userService.getUserById(followerId)).thenReturn(follower);
        when(userService.getUserById(followingId)).thenReturn(following);
        when(followersRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(false);
        when(followersMapper.toFollowers(dto)).thenReturn(mapped);
        when(followersRepository.save(mapped)).thenReturn(saved);
        when(followersMapper.toFollowersResponseDto(saved)).thenReturn(response);

        FollowersResponseDto result = followersService.followUser(dto);

        assertThat(result).isEqualTo(response);
    }
}
