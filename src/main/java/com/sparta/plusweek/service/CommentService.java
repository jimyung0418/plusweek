package com.sparta.plusweek.service;

import com.sparta.plusweek.dto.CommentRequestDto;
import com.sparta.plusweek.entity.Comment;
import com.sparta.plusweek.entity.Post;
import com.sparta.plusweek.repository.CommentRepository;
import com.sparta.plusweek.repository.PostRepository;
import com.sparta.plusweek.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        if (commentRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        Comment comment = new Comment(post, commentRequestDto, userDetails);
        commentRepository.save(comment);
    }
}
