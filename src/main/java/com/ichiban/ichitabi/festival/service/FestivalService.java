package com.ichiban.ichitabi.festival.service;

import com.ichiban.ichitabi.festival.dto.FestivalDto;
import com.ichiban.ichitabi.festival.mapper.FestivalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FestivalService {

    @Autowired
    private FestivalMapper festivalMapper;

    public List<FestivalDto> selectFestivalList(String season) {
        return festivalMapper.selectFestivalList(season);
    }

    public FestivalDto selectFestivalById(int id) {
        return festivalMapper.selectFestivalById(id);
    }

}
