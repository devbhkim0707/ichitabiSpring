package com.ichiban.ichitabi.user.mapper;

import com.ichiban.ichitabi.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByEmail(String email);
}


