package com.ichiban.ichitabi.common.image;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private Long ownerId;
    private String type;
    private String imgUrl;
    private String repImgYn;
}
