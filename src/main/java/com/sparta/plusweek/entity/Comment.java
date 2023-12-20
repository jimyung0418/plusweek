package com.sparta.plusweek.entity;

import com.sparta.plusweek.dto.CommentRequestDto;
import com.sparta.plusweek.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    public Comment(Post post, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        this.content = commentRequestDto.getContent();
        this.user = userDetails.getUser();
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent() == null ? this.getContent() : commentRequestDto.getContent();
    }
}
