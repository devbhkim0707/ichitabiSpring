package com.ichiban.ichitabi.festival.mapper;

import com.ichiban.ichitabi.festival.dto.FestivalDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FestivalMapper {

    List<FestivalDto> selectFestivalList(String season);

    FestivalDto selectFestivalById(int id);

    int insertFestival(FestivalDto festivalDto);

}
