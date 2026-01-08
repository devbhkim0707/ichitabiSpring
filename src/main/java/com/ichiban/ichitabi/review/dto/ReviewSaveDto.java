package com.ichiban.ichitabi.review.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ReviewSaveDto {
    private String title;
    private int rating;
    private String date;
    private String companion;
    private String content;
    private String hashtags;
    private List<String> hashtagList;

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
        if (hashtags != null && !hashtags.isEmpty()) {
            this.hashtagList = Arrays.asList(hashtags.split(","));
        }
    }
}
