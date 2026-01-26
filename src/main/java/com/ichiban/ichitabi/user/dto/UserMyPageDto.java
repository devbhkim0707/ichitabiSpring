package com.ichiban.ichitabi.user.dto;

import com.ichiban.ichitabi.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMyPageDto {
    private String email;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
    private String imgUrl;
}
