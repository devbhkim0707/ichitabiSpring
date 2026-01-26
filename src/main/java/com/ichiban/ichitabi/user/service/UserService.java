package com.ichiban.ichitabi.user.service;

import com.ichiban.ichitabi.image.ImageOwnerType;
import com.ichiban.ichitabi.image.dto.ImageDto;
import com.ichiban.ichitabi.image.service.ImageService;
import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.dto.UserMyPageDto;
import com.ichiban.ichitabi.user.form.UserUpdateForm;
import com.ichiban.ichitabi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    public int userSignup(UserDto userDto) {

        // password encoding
        String encodedPassword = passwordEncoder.encode(userDto.getPw());

        userDto.setPw(encodedPassword);

        return userMapper.userSignup(userDto);
    }

    public UserMyPageDto findMyPageInfo(Long userId) {
        return userMapper.findMyPageInfo(userId);
    }

    public Long updateUserInfo(
            String email,
            UserUpdateForm userUpdateForm,
            MultipartFile profileImage
    ) throws Exception {
        UserDto userDto = userMapper.findByEmail(email);
        ImageDto imageDto = imageService.selectUserImage(userDto.getId());

        userDto.setNickname(userUpdateForm.getNickname());

        if (userUpdateForm.getPassword() != null
                && !userUpdateForm.getPassword().isBlank()
                && userUpdateForm.getPassword().equals(userUpdateForm.getConfirmPassword())
        ) {
            String encodedPassword = passwordEncoder.encode(userUpdateForm.getPassword());

            userDto.setPw(encodedPassword);
        } else {
            userDto.setPw(null);
        }

        if (imageDto == null && !profileImage.isEmpty()) {
            ImageDto newImageDto = new ImageDto();

            newImageDto.setOwnerId(userDto.getId());
            newImageDto.setType(ImageOwnerType.USER);
            newImageDto.setRepImgYn("Y");

            imageService.saveUserImage(newImageDto, profileImage);
        } else if (imageDto != null && !profileImage.isEmpty()) {
            imageService.updateUserImage(userDto.getId(), profileImage);
        }

        return userMapper.updateUserInfo(userDto.getId(), userDto);
    }

    public UserDto login(String email, String pw) {
        UserDto userDto = userMapper.findByEmail(email);
        String encodedInputPassword = passwordEncoder.encode(pw);

        if (userDto == null) return null;

        if (!userDto.getPw().equals(encodedInputPassword)) return null;

        return userDto;
    }

    public Long findUserId(String email) {
        return Long.valueOf(userMapper.findUserId(email));
    }

    public String findNickname(String email) {
        return userMapper.findNickname(email);
    }
}
