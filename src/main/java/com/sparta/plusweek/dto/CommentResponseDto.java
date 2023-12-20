package com.sparta.plusweek.dto;

import com.sparta.plusweek.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
}
