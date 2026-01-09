package com.ichiban.ichitabi.review.dto;

import com.ichiban.ichitabi.user.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Data
public class ReviewListDto {

    private Long id;

    private String title;

    private String location;

    private LocalDate birthday;

    private Gender gender;

    private String imgUrl;

    private String hashtags;

    private List<String> hashtagList;

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


    public String getGenderText() {
        if (gender == Gender.MALE) return "남";
        if (gender == Gender.FEMALE) return "여";
        return "";
    }
}
