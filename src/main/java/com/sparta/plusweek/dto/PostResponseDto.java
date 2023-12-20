package com.sparta.plusweek.dto;


import com.sparta.plusweek.entity.Comment;
import com.sparta.plusweek.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto extends CommonResponseDto{

    private String title;
    private String nickname;
    private String content;
    private Long likes;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.likes = (long) post.getLikesList().size();
        this.createdAt = post.getCreatedAt();
        this.comments = commentsToDto(post.getCommentList());
    }

    // comment List -> commentResponseDto List
    private List<CommentResponseDto> commentsToDto(List<Comment> comments) {
        List<CommentResponseDto> commentsDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setContent(comment.getContent());
            commentResponseDto.setNickname(comment.getUser().getNickname());
            commentResponseDto.setCreatedAt(comment.getCreatedAt());
            commentsDtoList.add(commentResponseDto);
        }
        return commentsDtoList;
    }
}
