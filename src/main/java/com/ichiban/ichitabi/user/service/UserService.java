package com.ichiban.ichitabi.user.service;

import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public int userSignup(UserDto userDto) {

        // password encoding
        String encodedPassword = passwordEncoder.encode(userDto.getPw());

        userDto.setPw(encodedPassword);

        return userMapper.userSignup(userDto);
    }

    public UserDto login(String email, String pw) {
        UserDto userDto = userMapper.findByEmail(email);
        String encodedInputPassword = passwordEncoder.encode(pw);

        if (userDto == null) return null;

        if (!userDto.getPw().equals(encodedInputPassword)) return null;

        return userDto;
    }
}
