package com.ichiban.ichitabi.review.mapper;

import com.ichiban.ichitabi.review.dto.ReviewListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewListDto> selectReviewList();

    List<ReviewListDto> selectReviewListByHashtag(String hashtag);
}
