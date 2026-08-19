package com.hush0k.shareRoute.followers.internal;

import com.hush0k.shareRoute.followers.internal.dto.response.FollowingListDto;
import com.hush0k.shareRoute.followers.internal.dto.response.UserBriefWithFollowChecker;
import com.hush0k.shareRoute.followers.internal.exception.UserFollowThemselvesException;
import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.UserLookupPort;
import com.hush0k.shareRoute.followers.internal.dto.request.FollowersCreateDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersListDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersResponseDto;
import com.hush0k.shareRoute.followers.internal.exception.UserAlreadyFollowUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FollowersService {

    private final FollowersRepository followersRepository;
    private final FollowersMapper followersMapper;
    private final UserLookupPort userService;

    public FollowersResponseDto followUser(FollowersCreateDto dto) {
        if (dto.followerId().equals(dto.followingId())) {
            log.info("Followers with id: {} try to follow themselves", dto.followerId());
            throw new UserFollowThemselvesException(dto.followerId());
        }
        UserBriefDto follower = userService.getUserById(dto.followerId());
        UserBriefDto following = userService.getUserById(dto.followingId());

        boolean alreadyFollowing = followersRepository.existsByFollowerIdAndFollowingId(follower.id(),
                following.id());
        if (alreadyFollowing) {
            log.info("User {} is already following user {}", dto.followerId(), dto.followingId());
            throw new UserAlreadyFollowUserException(dto.followerId(), dto.followingId());
        }

        FollowersModel followers = followersMapper.toFollowersModel(dto);
        FollowersModel newFollower = followersRepository.save(followers);

        return followersMapper.toFollowersResponseDto(newFollower);
    }

    public FollowersListDto getFollowersList(UUID followingId, UUID currentUserId, Pageable pageable) {
        Page<UUID> followerIdsPage = followersRepository
                .findAllByFollowingId(followingId, pageable)
                .map(FollowersModel::getFollowerId);

        List<UUID> followerIds = followerIdsPage.getContent();

        if (followerIds.isEmpty()) {
            return new FollowersListDto(Page.empty(pageable));
        }

        Set<UUID> followedByCurrentUser = followersRepository.findFollowingIdsAmong(currentUserId, followerIds);

        List<UserBriefDto> userBriefs = userService.findBriefByUserId(followerIds);

        List<UserBriefWithFollowChecker> enrichedBriefs = userBriefs.stream()
                .map(brief -> new UserBriefWithFollowChecker(
                        brief.id(),
                        brief.firstName(),
                        brief.lastName(),
                        brief.nickname(),
                        brief.avatar(),
                        brief.rankId(),
                        followedByCurrentUser.contains(brief.id())
                ))
                .toList();

        Page<UserBriefWithFollowChecker> resultPage = new PageImpl<>(enrichedBriefs, pageable, followerIdsPage.getTotalElements());
        return new FollowersListDto(resultPage);
    }

    public FollowingListDto getFollowingList(UUID followerId, Pageable pageable) {
        Page<UUID> followingIdsPage = followersRepository
                .findAllByFollowerId(followerId, pageable)
                .map(FollowersModel::getFollowingId);

        List<UUID> followingIds = followingIdsPage.getContent();

        if (followingIds.isEmpty()) {
            return new FollowingListDto(Page.empty(pageable));
        }

        List<UserBriefDto> userBriefs = userService.findBriefByUserId(followingIds);

        Page<UserBriefDto> resultPage = new PageImpl<>(userBriefs, pageable, followingIdsPage.getTotalElements());

        return new FollowingListDto(resultPage);
    }

}
