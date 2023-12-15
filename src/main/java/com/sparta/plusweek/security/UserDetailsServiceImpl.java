package com.sparta.plusweek.security;

import com.sparta.plusweek.entity.User;
import com.sparta.plusweek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + nickname));
        return new UserDetailsImpl(user);
    }

}