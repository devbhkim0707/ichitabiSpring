package com.ichiban.ichitabi.festival.controller;

import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/festival")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/")
    public String festivalPage(
            @RequestParam(defaultValue = "ALL") String season,
            Model model
    ) {
        List<FestivalDto> festivalItems =
                festivalService.selectFestivalList(season);

        model.addAttribute("festivalItems", festivalItems);
        model.addAttribute("currentSeason", season);

        return "fragment/festival_carousel::festivalCarousel";
    }
}
