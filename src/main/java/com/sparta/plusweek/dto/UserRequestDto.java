package com.sparta.plusweek.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "닉네임은 최소 3자이상, 영문 대소문자나 숫자만 입력 가능합니다.")
    private String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private String password;

    private String checkPassword;
}
