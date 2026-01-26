package com.ichiban.ichitabi.festival.controller;

import com.ichiban.ichitabi.festival.Season;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.service.FestivalService;
import com.ichiban.ichitabi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/festival")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String festivalPage(
            @RequestParam(required = false) Season season,
            Model model
    ) {
        Season seasonEnum = (season != null) ? season : Season.ALL;

        List<FestivalDto> festivalItems = festivalService.selectFestivalList(seasonEnum);

        model.addAttribute("festivalItems", festivalItems);
        model.addAttribute("currentSeason", seasonEnum);

        return "fragment/festival_carousel::festivalCarousel";
    }

    @GetMapping("/list")
    public String festivalList(
//            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Season season,
            Model model
    ) {
        Season seasonEnum = (season != null) ? season : Season.ALL;

        List<FestivalDto> festivalList = festivalService.selectFestivalList(seasonEnum);

        model.addAttribute("festivalItems", festivalList);
        model.addAttribute("season", season);

        return "festival/festival_list";
    }

    @GetMapping("/detail")
    public String festivalDetail(
        @RequestParam Long festivalId,
        Model model
    ) {
        FestivalDto festivalDto = festivalService.selectFestivalById(festivalId);

        model.addAttribute("festival", festivalDto);

        return "festival/festival_detail";
    }

    @PostMapping("/like")
    public ResponseEntity likeInsert(
            @RequestParam("festivalId") Long festivalId,
            Principal principal
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = userService.findUserId(principal.getName());

        Map map = new HashMap();
        map.put("userId", userId);
        map.put("festivalId", festivalId);

        int result = festivalService.likeInsert(map);

        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    @DeleteMapping("/like")
    public ResponseEntity likeDelete(
            @RequestParam("festivalId") Long festivalId,
            Principal principal
    ) {
        Long userId = userService.findUserId(principal.getName());

        Map map = new HashMap();
        map.put("userId", userId);
        map.put("festivalId", festivalId);

        int result = festivalService.likeDelete(map);

        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    @GetMapping("/like")
    @ResponseBody
    public int likeCount(
            @RequestParam("festivalId") Long festivalId
    ) {
        return festivalService.likeCount(festivalId);
    }

    @GetMapping("/like/check")
    @ResponseBody
    public ResponseEntity<Boolean> isLiked(
            @RequestParam("festivalId") Long festivalId,
            Principal principal
    ) {
        if (principal == null) {
            return ResponseEntity.ok(false);
        }

        Long userId = userService.findUserId(principal.getName());
        boolean liked = festivalService.isLiked(festivalId, userId);

        return ResponseEntity.ok(liked);
    }
}
