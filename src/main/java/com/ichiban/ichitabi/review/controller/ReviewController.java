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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // review_detail 페이지
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

    // 좋아요 등록
    @PostMapping("/like")
    public ResponseEntity likeInsert(@RequestParam("reviewId") Long reviewId, Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = userService.findUserId(principal.getName());

        Map map = new HashMap();
        map.put("userId", userId);
        map.put("reviewId", reviewId);

        int result = reviewService.likeInsert(map);

        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    // 좋아요 취소
    @DeleteMapping("/like")
    public ResponseEntity likeDelete(@RequestParam("reviewId") Long reviewId, Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        Long userId = userService.findUserId(principal.getName());

        Map map = new HashMap();
        map.put("userId", userId);
        map.put("reviewId", reviewId);

        int result = reviewService.likeDelete(map);

        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    // 좋아요 갯수
    @GetMapping("/like")
    @ResponseBody
    public int likeCount(@RequestParam("reviewId") Long reviewId) {
        return reviewService.likeCount(reviewId);
    }

    // 좋아요 체크되어 있는지 확인
    @GetMapping("/like/check")
    @ResponseBody
    public ResponseEntity<Boolean> isLiked(@RequestParam("reviewId") Long reviewId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(false);
        }

        Long userId = userService.findUserId(principal.getName());
        boolean liked =  reviewService.isLiked(reviewId, userId);

        return ResponseEntity.ok(liked);
    }


    // 검색 결과 리스트
    @GetMapping("/search")
    public String searchResult(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("reviewList", reviewService.searchResult(keyword));

        return "reviews/search_result";
    }


}
