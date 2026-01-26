package com.ichiban.ichitabi.user.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateForm {

    @NotBlank(message = "닉네임은 필수 입니다.")
    @Size(max = 20, message = "닉네임은 최대 20자 까지 입니다.")
    private String nickname;

    private String imgUrl;

    private String password;

    private String confirmPassword;
}
