package com.sparta.plusweek.controller;

import com.sparta.plusweek.dto.CommonResponseDto;
import com.sparta.plusweek.dto.UserRequestDto;
import com.sparta.plusweek.jwt.JwtUtil;
import com.sparta.plusweek.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto,
                                                    BindingResult bindingResult) {
        // 유효성 검사 오류가 있는 경우
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(", ");
            }
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());  // 마지막 ", " 제거

            return ResponseEntity.badRequest().body(new CommonResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value()));
        }

        try {
            userService.signup(userRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입 성공!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto,
                                                   HttpServletResponse httpServletResponse) {
        try {
            userService.login(userRequestDto);
            httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(userRequestDto.getNickname()));
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공!", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
