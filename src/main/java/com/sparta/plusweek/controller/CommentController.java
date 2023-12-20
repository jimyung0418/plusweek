package com.sparta.plusweek.controller;

import com.sparta.plusweek.dto.CommentRequestDto;
import com.sparta.plusweek.dto.CommentResponseDto;
import com.sparta.plusweek.dto.CommonResponseDto;
import com.sparta.plusweek.security.UserDetailsImpl;
import com.sparta.plusweek.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    ResponseEntity<CommonResponseDto> createComment(@PathVariable Long postId,
                                                    @RequestBody CommentRequestDto commentRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.createComment(postId, commentRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 작성 완료!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/comments/{commentId}")
    ResponseEntity<CommonResponseDto> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentRequestDto commentRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.updateComment(commentId, commentRequestDto, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("수정 완료!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/comments/{commentId}")
    ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제 완료!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
