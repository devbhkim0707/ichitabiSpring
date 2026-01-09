package com.ichiban.ichitabi.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String adminMainPage() {
        return "/admin/admin_reviews";
    }

    @GetMapping("/review")
    public String adminReview() {
        return "/admin/admin_reviews";
    }

    @GetMapping("/user")
    public String adminUser() {
        return "/admin/admin_users";
    }

    @GetMapping("/festival")
    public String adminFestival() {
        return "/admin/admin_festivals";
    }
}
