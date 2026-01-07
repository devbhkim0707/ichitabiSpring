package com.ichiban.ichitabi.user.service;

import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserDto login(String email, String pw) {
        UserDto user = userMapper.findByEmail(email);

        if (user == null) return null;


        if (!user.getPw().equals(pw)) return null;

        return user;
    }
}
