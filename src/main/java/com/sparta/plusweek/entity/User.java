package com.sparta.plusweek.entity;

import com.sparta.plusweek.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public User(UserRequestDto userRequestDto, String encodedPassword) {
        this.nickname = userRequestDto.getNickname();
        this.password = encodedPassword;
    }
}
