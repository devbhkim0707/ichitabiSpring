package com.ichiban.ichitabi.review.controller;

import com.ichiban.ichitabi.review.dto.ReviewDetailDto;
import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.service.ReviewService;
import com.ichiban.ichitabi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

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
    public String reviewDetail(@PathVariable Long id, Principal principal, Model model) {

        ReviewDetailDto reviewDetailDto = reviewService.selectReviewDetail(id);

        boolean isOwner = false;

        String nickname = null;
        if (principal != null) {
            Long userId = userService.findUserId(principal.getName());
            nickname = userService.findNickname(principal.getName());

            isOwner = userId.equals(reviewDetailDto.getUserId());
        }


        model.addAttribute("reviewDetail", reviewDetailDto);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("nickname", nickname);

        return "reviews/detail";
    }


}
