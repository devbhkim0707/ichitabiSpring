package com.ichiban.ichitabi.user.dto;

import com.ichiban.ichitabi.user.Gender;
import com.ichiban.ichitabi.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String pw;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
    private UserType type;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}

