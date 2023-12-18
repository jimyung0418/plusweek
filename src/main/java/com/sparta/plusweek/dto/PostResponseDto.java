package com.sparta.plusweek.dto;


import com.sparta.plusweek.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto extends CommonResponseDto{

    private String title;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
