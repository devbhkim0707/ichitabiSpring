package com.ichiban.ichitabi.user.mapper;

import com.ichiban.ichitabi.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int userSignup(UserDto userDto);
    UserDto userLogin(String email);
    UserDto findByEmail(String email);
}


