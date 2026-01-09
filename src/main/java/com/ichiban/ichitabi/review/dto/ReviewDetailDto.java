package com.ichiban.ichitabi.review.dto;

import com.ichiban.ichitabi.user.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Gender gender;

    private int likeCount;

    private List<String> imgUrl;

    private String hashtags;

    private List<String> hashtagList;

    public String companionText() {
        switch(companion) {
            case 1: return "비즈니스";
            case 2: return "단독";
            case 3: return "가족";
            case 4: return "친구";
            case 5: return "연인";
            default: return "";
        }
    }

    public String getGenderText() {
        if (gender == Gender.MALE) return "남";
        if (gender == Gender.FEMALE) return "여";
        return "";
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
        if (hashtags != null && !hashtags.isEmpty()) {
            this.hashtagList = Arrays.asList(hashtags.split(","));
        }
    }

    public int getAge() {
        if (birthday == null) return 0;
        int age = LocalDate.now().getYear() - birthday.getYear();
        return (age/10) * 10;
    }

    public List<String> getImgUrl() {
        return imgUrl == null ? new ArrayList<>() : imgUrl;
    }
}
