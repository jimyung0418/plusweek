package com.sparta.plusweek.dto;


import com.sparta.plusweek.entity.Post;

import java.time.LocalDateTime;

public class PostResponseDto {

    private String title;
    private String nickname;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
    }
}
