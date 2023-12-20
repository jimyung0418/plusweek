package com.sparta.plusweek.service;

import com.sparta.plusweek.dto.CommentRequestDto;
import com.sparta.plusweek.entity.Comment;
import com.sparta.plusweek.entity.Post;
import com.sparta.plusweek.repository.CommentRepository;
import com.sparta.plusweek.repository.PostRepository;
import com.sparta.plusweek.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = checkCommentAndUser(commentId, userDetails);
        comment.update(commentRequestDto);
    }

    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = checkCommentAndUser(commentId, userDetails);
        commentRepository.delete(comment);
    }

    private Comment checkCommentAndUser(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );

        if (!Objects.equals(comment.getUser().getUserId(), userDetails.getUser().getUserId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정 및 삭제 가능합니다.");
        }
        return comment;
    }
}
