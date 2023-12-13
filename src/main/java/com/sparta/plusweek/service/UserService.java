package com.sparta.plusweek.service;

import com.sparta.plusweek.dto.UserRequestDto;
import com.sparta.plusweek.entity.User;
import com.sparta.plusweek.jwt.JwtUtil;
import com.sparta.plusweek.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(UserRequestDto userRequestDto) {

        String nickname = userRequestDto.getNickname();
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        // 중복 닉네임 확인
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 닉네임과 같은 값이 비밀번호에 포함되어있는지 확인
        if (isPasswordContainsNickname(userRequestDto)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다.");
        }

        // 비밀번호 확인이 비밀번호와 정확하게 일치하는지 확인
        if (!Objects.equals(userRequestDto.getPassword(), userRequestDto.getCheckPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 회원 정보 저장
        User user = new User(userRequestDto, encodedPassword);
        userRepository.save(user);
    }

    public void login(UserRequestDto userRequestDto, HttpServletResponse httpServletResponse) {
        String nickname = userRequestDto.getNickname();
        String password = userRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getNickname());
        jwtUtil.addJwtToCookie(token, httpServletResponse);
    }

    // 닉네임이 비밀번호에 포함되어 있는지 검증하는 메서드

    private boolean isPasswordContainsNickname(UserRequestDto userRequestDto) {
        return userRequestDto.getPassword().contains(userRequestDto.getNickname());
    }
}
