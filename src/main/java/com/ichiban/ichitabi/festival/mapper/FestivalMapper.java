package com.ichiban.ichitabi.festival.mapper;

import com.ichiban.ichitabi.festival.dto.FestivalDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FestivalMapper {

    int insertFestival(FestivalDto festivalDto);

}
