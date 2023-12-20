package com.sparta.plusweek.entity;

import com.sparta.plusweek.dto.PostRequestDto;
import com.sparta.plusweek.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<LikePost> likesList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = userDetails.getUser();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle() == null ? this.getTitle() : postRequestDto.getTitle();
        this.content = postRequestDto.getContent() == null ? this.getContent() : postRequestDto.getContent();
    }
}
