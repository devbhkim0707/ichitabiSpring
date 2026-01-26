package com.ichiban.ichitabi.user.mapper;

import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.dto.UserMyPageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int userSignup(UserDto userDto);
    UserDto userLogin(String email);
    UserDto findByEmail(String email);
    UserMyPageDto findMyPageInfo(Long userId);
    Long updateUserInfo(Long userId, UserDto userDto);
    Long findUserId(String email);
    String findNickname(String email);
    String findGender(Long userId);
}


