package com.example.musicBox.model.mapper;

import com.example.musicBox.model.dto.SongHistoryDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.SongHistory;
import com.example.musicBox.model.entity.UserEntity;

public class SongHistoryMapper {

 public  static  SongHistoryDto ToSongHistoryDto ( SongHistory songHistory){
     return new SongHistoryDto();
 }


}
