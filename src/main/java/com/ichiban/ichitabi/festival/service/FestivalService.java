package com.ichiban.ichitabi.festival.service;

import com.ichiban.ichitabi.festival.Season;
import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.mapper.FestivalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FestivalService {

    @Autowired
    private FestivalMapper festivalMapper;

    public List<FestivalDto> selectFestivalList(Season season) {
        return festivalMapper.selectFestivalList(season.name());
    }

    public List<FestivalDto> selectFestivalListPaging(Season season, int pageSize, int offset) {
        return festivalMapper.selectFestivalListPaging(season.name(), pageSize, offset);
    }

    public int countFestival(Season season) {
        return festivalMapper.countFestival(season.name());
    }

    public FestivalDto selectFestivalById(Long id) {
        return festivalMapper.selectFestivalById(id);
    }

}
