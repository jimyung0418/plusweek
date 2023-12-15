package com.sparta.plusweek.controller;

import com.sparta.plusweek.dto.PostResponseDto;
import com.sparta.plusweek.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

