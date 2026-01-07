package com.ichiban.ichitabi.review.controller;

import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //.홈 화면 해시태그별 리스트 호출
    @GetMapping("/")
    public String reviewListHtml(@RequestParam String hashtag, Model model) {
        model.addAttribute("reviewList", reviewService.selectReviewListByHashtag(hashtag));
        return "fragment/review_item_list";
    }


    @GetMapping("/write")
    public String writeReview() {
        return "reviews/write";
    }

    // 여행지 추천 리스트 호출
    @GetMapping("/reviews")
    public String reviewsList(Model model) {
        List<ReviewListDto> reviewListDtos = reviewService.selectReviewList();

        model.addAttribute("reviewList", reviewListDtos);

        return "reviews/reviews";
    }

//    temporary mapping -> to be updated using reviewId value
    @GetMapping("/detail/{id}")
    public String reviewDetail() {
        return "reviews/detail";
    }
}
