package com.ichiban.ichitabi.review.service;

import com.ichiban.ichitabi.review.dto.ReviewListDto;
import com.ichiban.ichitabi.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public List<ReviewListDto> selectReviewList() {
        return reviewMapper.selectReviewList();
    }

    public List<ReviewListDto> selectReviewListByHashtag(String hashtag) {
        return reviewMapper.selectReviewListByHashtag(hashtag);
    }

}
