package com.ichiban.ichitabi.review.service;

import com.ichiban.ichitabi.review.dto.ReviewDetailDto;
import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.dto.ReviewSaveDto;
import com.ichiban.ichitabi.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public List<ReviewListDto> selectReviewList() {
        return reviewMapper.selectReviewList();
    }

    @Transactional(readOnly = true)
    public List<ReviewListDto> selectReviewListByHashtag(String hashtag) {
        return reviewMapper.selectReviewListByHashtag(hashtag);
    }

    @Transactional(readOnly = true)
    public ReviewDetailDto selectReviewDetail(Long id) {
        return reviewMapper.selectReviewDetail(id);
    }

    public int likeInsert(Map map) {
        return reviewMapper.likeInsert(map);
    }

    public int likeDelete(Map map) {
        return reviewMapper.likeDelete(map);
    }

    public int likeCount(Long reviewId) {
        return reviewMapper.likeCount(reviewId);
    }

    public boolean isLiked(Long reviewId, Long userId) {
        return reviewMapper.isLiked(reviewId, userId) > 0;
    }

    public List<ReviewListDto> searchResult(String keyword) {
        return reviewMapper.searchResult(keyword);
    }

    @Transactional
    public Long saveOrUpdate(ReviewSaveDto dto, Long userId) {
        if (dto.getId() == null) {
            reviewMapper.insertReview(dto, userId); // 게시글 저장
        } else {
            reviewMapper.updateReview(dto); // 게시글 수정
            reviewMapper.deleteHashtagsByReviewId(dto.getId()); // 기존 태그 삭제
        }

        // 새 해시태그 저장 (List<String> hashtagList 순회)
        if (dto.getHashtags() != null) {
            for (String tag : dto.getHashtagList()) {
                reviewMapper.insertHashtag(dto.getId(), tag);
            }
        }
        return dto.getId();
    }

    @Transactional
    public void softDelete(Long id) {
        reviewMapper.softDelete(id);
    }


}
