package com.ichiban.ichitabi.user.controller;

import com.ichiban.ichitabi.user.UserType;
import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.form.UserSignUpForm;
import com.ichiban.ichitabi.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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

    /* ================= 로그인 ================= */

    // 로그인 페이지
    @GetMapping("login")
    public String userLoginForm() {
        return "/user/sign_in";
    }


    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요");
        return "/user/sign_in";
    }

    /* ================= 로그아웃 ================= */

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
