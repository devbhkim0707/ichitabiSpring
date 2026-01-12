package com.ichiban.ichitabi.review.mapper;

import com.ichiban.ichitabi.review.dto.ReviewDetailDto;
import com.ichiban.ichitabi.review.dto.ReviewListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {

    List<ReviewListDto> selectReviewList();

    List<ReviewListDto> selectReviewListByHashtag(String hashtag);

    ReviewDetailDto selectReviewDetail(Long id);

    int likeInsert(Map map);

    int likeDelete(Map map);

    int likeCount(Long reviewId);

    int isLiked(Long reviewId, Long userId);
}
