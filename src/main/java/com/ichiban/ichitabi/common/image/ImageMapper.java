package com.ichiban.ichitabi.common.image;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    int insertImage(ImageDto imageDto);


}
