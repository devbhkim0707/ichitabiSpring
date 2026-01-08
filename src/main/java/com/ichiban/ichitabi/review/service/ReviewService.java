package com.ichiban.ichitabi.review.service;

import com.ichiban.ichitabi.review.dto.ReviewDetailDto;
import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
