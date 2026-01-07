package com.ichiban.ichitabi.user.controller;

import com.ichiban.ichitabi.user.dto.UserDto;
import com.ichiban.ichitabi.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /* ================= 로그인 ================= */

    // 로그인 페이지
    @GetMapping("login")
    public String userLoginForm() {
        return "user/sign_in";
    }

    @PostMapping("login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody UserDto userDto,
                                     HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        UserDto loginUser = userService.login(
                userDto.getEmail(),
                userDto.getPw()
        );

        if (loginUser == null) {
            result.put("success", false);
            result.put("message", "이메일 또는 비밀번호가 틀렸습니다.");
            return result;
        }

        session.setAttribute("loginUser", loginUser);
        result.put("success", true);
        return result;
    }


    /* ================= 로그아웃 ================= */

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
