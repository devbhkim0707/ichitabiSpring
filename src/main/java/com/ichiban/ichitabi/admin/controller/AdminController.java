package com.ichiban.ichitabi.admin.controller;

import com.ichiban.ichitabi.admin.form.AdminFestivalForm;
import com.ichiban.ichitabi.admin.service.AdminService;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

    @GetMapping("/festival/new")
    public String adminFestivalNew() {
        return "/admin/admin_festival_edit";
    }

    @PostMapping("/festival/new")
    public String createFestival(
            @Valid AdminFestivalForm adminFestivalForm,
            BindingResult bindingResult,
            Model model
            ) {
        if (bindingResult.hasErrors()) {
            return "/admin/admin_festival_edit";
        }

        try {
            FestivalDto festivalDto = new FestivalDto();

            festivalDto.setTitle(adminFestivalForm.getTitle());
            festivalDto.setSeason(adminFestivalForm.getSeason());
            festivalDto.setContent(adminFestivalForm.getContent());
            festivalDto.setImgUrl(adminFestivalForm.getImgUrl());

            adminService.insertFestival(festivalDto);

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "/admin/admin_festival_edit";
        }

        return "/admin/admin_festivals";
    }
}
