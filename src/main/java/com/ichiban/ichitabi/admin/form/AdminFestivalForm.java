package com.ichiban.ichitabi.admin.form;

import com.ichiban.ichitabi.festival.Season;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminFestivalForm {
    
    @NotBlank(message = "축제 제목은 필수입니다")
    private String title;
    
    @NotNull(message = "계절은 필수입니다")
    private Season season;
    
    @NotBlank(message = "축제 상세 소개는 필수입니다")
    private String content;
    
    @NotBlank(message = "축제 이미지는 필수입니다")
    private String imgUrl;
    
}
