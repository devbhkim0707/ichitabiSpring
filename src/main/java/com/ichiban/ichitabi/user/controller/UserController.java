package com.ichiban.ichitabi.user.controller;

import com.ichiban.ichitabi.user.UserType;
import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.dto.UserMyPageDto;
import com.ichiban.ichitabi.user.form.UserSignUpForm;
import com.ichiban.ichitabi.user.form.UserUpdateForm;
import com.ichiban.ichitabi.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* ================= 회원가입 ================= */

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "user/sign_up";
    }

    // 회원가입 POST method
    @PostMapping("/signup")
    public String userSignup(
            @Valid UserSignUpForm userSignUpForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes rttr
    ) {
        if (bindingResult.hasErrors()) {
            return "/user/sign_up";
        }

        try {
            UserDto userDto = new UserDto();
            userDto.setEmail(userSignUpForm.getEmail());
            userDto.setPw(userSignUpForm.getPassword());
            userDto.setNickname(userSignUpForm.getNickname());
            userDto.setBirthday(userSignUpForm.getBirth());
            userDto.setGender(userSignUpForm.getGender());
            userDto.setType(UserType.USER);

            userService.userSignup(userDto);

            rttr.addFlashAttribute("resultMessage", "회원가입을 환영합니다");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "/user/sign_up";
        }

        return "/user/sign_in";
    }

    /* ================= 마이페이지 ================= */

    @GetMapping("/mypage")
    public String userMyPage(
            Principal principal,
            Model model
    ) {
        Long userId = userService.findUserId(principal.getName());
        UserMyPageDto userMyPageDto = userService.findMyPageInfo(userId);

        model.addAttribute("user", userMyPageDto);

        return "/user/mypage";
    }

    @GetMapping("/mypage/edit")
    public String userMyPageEdit(
            Principal principal,
            Model model
    ) {
        Long userId = userService.findUserId(principal.getName());
        UserMyPageDto userMyPageDto = userService.findMyPageInfo(userId);

        model.addAttribute("userUpdate", userMyPageDto);

        return "/user/mypage_edit";
    }

    @PostMapping("/mypage/edit")
    public String updateMyPage(
            @Valid UserUpdateForm userUpdate,
            BindingResult bindingResult,
            Principal principal,
            @RequestParam("profileImage") MultipartFile profileImage
    ) {
        if (bindingResult.hasErrors()) {
            return "/user/mypage_edit";
        }

        try {
            userService.updateUserInfo(principal.getName(), userUpdate, profileImage);
        } catch (Exception e) {
            return "/user/mypage_edit";
        }

        return "redirect:/user/mypage";
    }


    /* ================= 로그인 ================= */

    // 로그인 페이지
    @GetMapping("/login")
    public String userLoginForm() {
        return "/user/sign_in";
    }


    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요");
        return "/user/sign_in";
    }

    /* ================= 로그아웃 ================= */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
