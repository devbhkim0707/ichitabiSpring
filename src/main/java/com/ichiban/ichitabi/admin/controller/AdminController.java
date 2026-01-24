package com.ichiban.ichitabi.admin.controller;

import com.ichiban.ichitabi.admin.form.AdminFestivalForm;
import com.ichiban.ichitabi.admin.service.AdminService;
import com.ichiban.ichitabi.common.PageHandler;
import com.ichiban.ichitabi.festival.Season;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.service.FestivalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final FestivalService festivalService;

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
    public String adminFestival(
            @RequestParam(required = false) Season season,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        Season seasonEnum = (season != null) ? season : Season.ALL;

        int pageSize = 8;

        int totalCnt = festivalService.countFestival(seasonEnum);

        PageHandler pageHandler = new PageHandler(totalCnt, pageSize, page);

        List<FestivalDto> festivalsList = festivalService
                .selectFestivalListPaging(
                        seasonEnum,
                        pageHandler.getLimit(),
                        pageHandler.getOffset()
                );

        model.addAttribute("festivalsList", festivalsList);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("season", seasonEnum);

        return "/admin/admin_festivals";
    }

    @GetMapping("/festival/new")
    public String adminFestivalNew(
            Model model
    ) {
        FestivalDto emptyFestival = new FestivalDto();

        model.addAttribute("isEditing", false);
        model.addAttribute("festival", emptyFestival);

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

        return "redirect:/admin/festival";
    }

    @GetMapping("/festival/edit/{id}")
    public String adminFestivalEdit(
            @PathVariable("id") Long festivalId,
            Model model
    ) {
        FestivalDto festival = festivalService.selectFestivalById(festivalId);

        model.addAttribute("isEditing", true);
        model.addAttribute("festival", festival);

        return "/admin/admin_festival_edit";
    }

    @PostMapping("/festival/edit/{id}")
    public String updateFestival(
            @PathVariable("id") Long festivalId,
            @Valid AdminFestivalForm adminFestivalForm,
            BindingResult bindingResult,
            Model model
    ) {
        FestivalDto festival = festivalService.selectFestivalById(festivalId);

        festival.setTitle(adminFestivalForm.getTitle());
        festival.setContent(adminFestivalForm.getContent());
        festival.setSeason(adminFestivalForm.getSeason());
        festival.setImgUrl(adminFestivalForm.getImgUrl());
        festival.setUpdatedAt(LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEditing", true);
            model.addAttribute("festival", festival);

            return "/admin/admin_festival_edit";
        }

        try {
            adminService.updateFestival(festival);
        } catch (Exception e) {
            model.addAttribute("isEditing", true);
            model.addAttribute("festival", festival);
            model.addAttribute("errorMessage", e.getMessage());

            return "/admin/admin_festival_edit";
        }

        return "redirect:/admin/festival";
    }

    @PostMapping("/festival/delete/{festivalId}")
    public String deleteFestival(
            @PathVariable("festivalId") Long festivalId
    ) {
        adminService.deleteFestival(festivalId);

        return "redirect:/admin/festival";
    }

}
