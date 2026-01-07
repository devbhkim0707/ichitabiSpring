package com.ichiban.ichitabi.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String pw;
    private String nickname;
    private LocalDate birthday;
    private String gender;
    private String type;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}

