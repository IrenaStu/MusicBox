package com.example.musicBox.model.mapper;

import com.example.musicBox.model.dto.SongDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.enums.SongStatus;
import com.example.musicBox.model.param.SongParam;

public class SongMapper {


    public static SongEntity toSongEntity(SongParam songParam){
        SongEntity song = new SongEntity();
        song.setSongName(songParam.getSongName());
        song.setUrl(songParam.getUrl());
        song.setStatus(SongStatus.PUBLISHED);
        return song;
    }


    public  static SongDto ToSongDto (SongEntity song){
        return new SongDto(song);
    }
}
