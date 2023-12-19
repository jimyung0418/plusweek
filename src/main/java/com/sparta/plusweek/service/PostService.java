package com.sparta.plusweek.service;

import com.sparta.plusweek.dto.PostRequestDto;
import com.sparta.plusweek.dto.PostResponseDto;
import com.sparta.plusweek.entity.Post;
import com.sparta.plusweek.repository.PostRepository;
import com.sparta.plusweek.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        // 예외 처리
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        // post 저장
        Post post = new Post(postRequestDto, userDetails);
        postRepository.save(post);
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return new PostResponseDto(post);
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        // 예외처리
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (!Objects.equals(post.getUser().getUserId(), userDetails.getUser().getUserId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정 가능합니다.");
        }

        post.update(postRequestDto);
    }
}
