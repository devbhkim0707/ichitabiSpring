package com.ichiban.ichitabi.admin.service;

import com.ichiban.ichitabi.image.dto.ImageDto;
import com.ichiban.ichitabi.image.mapper.ImageMapper;
import com.ichiban.ichitabi.image.ImageOwnerType;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.mapper.FestivalMapper;
import com.ichiban.ichitabi.review.mapper.ReviewMapper;
import com.ichiban.ichitabi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final FestivalMapper festivalMapper;
    private final ImageMapper imageMapper;

    public int insertFestival(FestivalDto festivalDto) {

        // festival insert 후 image insert
        festivalMapper.insertFestival(festivalDto);

        Long festivalId = festivalDto.getId();

        ImageDto imageDto = new ImageDto();
        imageDto.setOwnerId(festivalId);
        imageDto.setType(ImageOwnerType.FESTIVAL);
        imageDto.setImgUrl(festivalDto.getImgUrl());
        imageDto.setRepImgYn("Y");

        return imageMapper.insertImage(imageDto);
    }

    public void updateFestival(FestivalDto festivalDto) {
        festivalMapper.updateFestival(festivalDto);
        festivalMapper.updateFestivalImage(festivalDto);
    }

    public void deleteFestival(Long festivalId) {
        festivalMapper.deleteFestival(festivalId);
    }

}
