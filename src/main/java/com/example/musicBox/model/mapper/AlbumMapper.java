package com.example.musicBox.model.mapper;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.AlbumDto;
import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.param.AlbumParam;

import java.util.Set;

public class AlbumMapper {

    public  static AlbumEntity toAlbumEntity(AlbumParam albumParam){
        AlbumEntity album = new AlbumEntity();
        album.setAlbumName(albumParam.getAlbumName());
        album.setStatus(AlbumStatus.PUBLISHED);
        return album;
    }

    public static AlbumDto albumDto(AlbumEntity album){
        return new AlbumDto(album);
    }
}
