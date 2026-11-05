package com.hush0k.shareRoute.post.internal.service;

import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.post.internal.domain.Post;
import com.hush0k.shareRoute.post.internal.dto.request.PostCreateDto;
import com.hush0k.shareRoute.post.internal.dto.request.PostUpdateDto;
import com.hush0k.shareRoute.post.internal.dto.response.PostResponseDto;
import com.hush0k.shareRoute.post.internal.exception.PostAccessDeniedException;
import com.hush0k.shareRoute.post.internal.exception.PostNotFoundException;
import com.hush0k.shareRoute.post.internal.mapper.PostMapper;
import com.hush0k.shareRoute.post.internal.repository.PostRepository;
import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.UserLookupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserLookupPort userLookupPort;

    public PostResponseDto createPost(PostCreateDto postCreateDto, UUID userId) {
        UserBriefDto author = userLookupPort.getUserById(userId);
        Post saved = postRepository.save(postMapper.toPost(postCreateDto, userId));
        return postMapper.toPostResponseDto(saved, author);
    }


    public PostResponseDto updatePost(UUID postId, PostUpdateDto postUpdateDto, UUID userId) {
        Post post = postIsExist(postId);
        checkOwner(post, userId);
        UserBriefDto author = userLookupPort.getUserById(userId);
        postMapper.updatePostFromDto(postUpdateDto, post);
        return postMapper.toPostResponseDto(post, author);
    }

    public void deletePost(UUID postId, UUID userId) {
        Post post = postIsExist(postId);
        checkOwner(post, userId);
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(UUID postId) {
        Post post = postIsExist(postId);
        UserBriefDto author = userLookupPort.getUserById(post.getUserId());
        return postMapper.toPostResponseDto(post, author);
    }

    @Transactional(readOnly = true)
    public PageResponse<PostResponseDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<UUID> authorIds = posts.stream().map(Post::getUserId).distinct().toList();
        Map<UUID, UserBriefDto> authors = userLookupPort.findBriefByUserId(authorIds).stream()
                .collect(Collectors.toMap(UserBriefDto::id, Function.identity()));
        return PageResponse.of(posts.map(post -> postMapper.toPostResponseDto(post, authors.get(post.getUserId()))));
    }

    private void checkOwner(Post post, UUID userId) {
        if (!post.getUserId().equals(userId)) {
            log.warn("User {} tried to modify post {} owned by user {}", userId, post.getId(), post.getUserId());
            throw new PostAccessDeniedException(post.getId());
        }
    }

    private Post postIsExist(UUID postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> {
                    log.warn("Post with id {} not found", postId);
                    return new PostNotFoundException(postId);
                }
        );
    }

}
