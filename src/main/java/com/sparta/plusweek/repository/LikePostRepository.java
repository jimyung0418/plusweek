package com.sparta.plusweek.repository;

import com.sparta.plusweek.entity.LikePost;
import com.sparta.plusweek.entity.Post;
import com.sparta.plusweek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByPost(Post post);

    LikePost findByPostAndUser(Post post, User user);
}
