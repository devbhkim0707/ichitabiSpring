package com.ichiban.ichitabi.image.dto;

import com.ichiban.ichitabi.image.ImageOwnerType;
import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private Long ownerId;
    private ImageOwnerType type;
    private String imgUrl;
    private String repImgYn;
}
