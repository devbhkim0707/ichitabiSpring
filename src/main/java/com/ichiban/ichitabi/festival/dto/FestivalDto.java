package com.ichiban.ichitabi.festival.dto;

import com.ichiban.ichitabi.festival.Season;
import lombok.Data;

@Data
public class FestivalDto {

    private Long id;
    private String title;
    private Season season;
    private String content;
    private String imgUrl;
}
