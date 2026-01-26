package com.ichiban.ichitabi.review.controller;

import com.ichiban.ichitabi.review.dto.ReviewDetailDto;
import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.dto.ReviewSaveDto;
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


    // 저장 및 수정 로직 (작성 페이지에서 '작성' 버튼 클릭 시 호출)
    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<?> saveOrUpdate(@RequestBody ReviewSaveDto reviewDto, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Long userId = userService.findUserId(principal.getName());

        // 서비스에서 INSERT 또는 UPDATE를 처리하고 생성된 ID를 받아옴
        Long savedId = reviewService.saveOrUpdate(reviewDto, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedId);
        return ResponseEntity.ok(response);
    }

    // 수정 페이지 이동 (상세 페이지에서 '수정' 클릭 시 호출)
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        ReviewDetailDto review = reviewService.selectReviewDetail(id);
        Long userId = userService.findUserId(principal.getName());

        // 본인 확인
        if (!userId.equals(review.getUserId())) {
            return "redirect:/review/reviews";
        }

        model.addAttribute("review", review);
        model.addAttribute("isEdit", true);
        return "reviews/write"; // write.html을 다시 사용
    }

    // 논리 삭제 (상세 페이지에서 '삭제' 클릭 시 호출)
    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        ReviewDetailDto review = reviewService.selectReviewDetail(id);
        Long userId = userService.findUserId(principal.getName());

        if (userId.equals(review.getUserId())) {
            reviewService.softDelete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }



    @GetMapping("/recommend")
    public String recommendReview(Principal principal, Model model) {
        Long userId = userService.findUserId(principal.getName());
        List<ReviewListDto> reviewListDtos = reviewService.recommendReview(userId);

        model.addAttribute("reviewList", reviewListDtos);

        return "reviews/reviews";
    }

}
