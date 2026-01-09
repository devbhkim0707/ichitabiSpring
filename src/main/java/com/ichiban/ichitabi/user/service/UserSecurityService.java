package com.ichiban.ichitabi.user.service;

import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = userMapper.userLogin(username);

        if (userDto == null) {
            throw new UsernameNotFoundException("해당 이메일로 가입된 회원을 찾을 수 없습니다.");
        }

        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPw())
                .roles(userDto.getType().toString())
                .build();
    }
}
