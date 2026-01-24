package com.ichiban.ichitabi.festival.mapper;

import com.ichiban.ichitabi.festival.Season;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FestivalMapper {

    List<FestivalDto> selectFestivalList(String season);

    List<FestivalDto> selectFestivalListPaging(String season, int pageSize, int offset);

    int countFestival(String season);

    int updateFestival(FestivalDto festivalDto);

    int updateFestivalImage(FestivalDto festivalDto);

    int deleteFestival(Long festivalId);

    FestivalDto selectFestivalById(Long id);

    int insertFestival(FestivalDto festivalDto);

    int likeInsert(Map map);

    int likeDelete(Map map);

    int likeCount(Long festivalId);

    int isLiked(Long festivalId, Long userId);

}
