package com.example.musicBox.model.mapper;

import com.example.musicBox.model.dto.PlayListDto;
import com.example.musicBox.model.entity.PlayListEntity;
import com.example.musicBox.model.enums.PlayListStatus;
import com.example.musicBox.model.param.PlayListParam;

public class PlayListMapper {


    public static PlayListEntity toPlayList(PlayListParam param){

        PlayListEntity playList = new PlayListEntity();
        playList.setPlayListName(param.getPlayListName());
        playList.setStatus(PlayListStatus.PUBLISHED);
        return playList;
    }

    public static PlayListDto toPlayListDto(PlayListEntity playList){
        return new PlayListDto(playList);
    }
}
