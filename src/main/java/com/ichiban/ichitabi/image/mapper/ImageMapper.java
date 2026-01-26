package com.ichiban.ichitabi.image.mapper;

import com.ichiban.ichitabi.image.ImageOwnerType;
import com.ichiban.ichitabi.image.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    int insertImage(ImageDto imageDto);

    int insertImageFile(ImageDto imageDto);

    int updateImageFile(ImageDto imageDto);

    ImageDto selectRepImage(Long userId, ImageOwnerType type);
}
