package com.ichiban.ichitabi.review.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReviewDetailDto {

    private Long id;

    private String title;

    private int companion;

    private String location;

    private int rating;

    private String content;

    private String date;

    private String nickname;

    private LocalDate birthday;

    private int gender;

    private int likeCount;

    private List<String> imgUrl;
}
