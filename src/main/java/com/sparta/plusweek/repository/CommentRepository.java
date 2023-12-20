package com.sparta.plusweek.repository;

import com.sparta.plusweek.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
