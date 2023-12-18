package com.sparta.plusweek.controller;

import com.sparta.plusweek.dto.CommonResponseDto;
import com.sparta.plusweek.dto.PostRequestDto;
import com.sparta.plusweek.dto.PostResponseDto;
import com.sparta.plusweek.security.UserDetailsImpl;
import com.sparta.plusweek.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")

public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.createPost(postRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("게시글 작성 완료!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}

