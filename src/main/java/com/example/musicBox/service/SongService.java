package com.example.musicBox.service;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.SongDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.enums.SongStatus;
import com.example.musicBox.model.mapper.SongMapper;
import com.example.musicBox.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

  private final SongRepository songRepository;



    public Page<SongDto> viewAllSongsByName (String songName, Integer page, Integer size ){
      Page<SongEntity> songsToView = songRepository.findBySongName(songName,SongStatus.DELETED, PageRequest.of(page, size));
      if (songsToView.isEmpty()) {
        throw new CustomException(ErrorMessage.SONGS_NOT_FOUND);
      }
      return songsToView.map(SongDto::new);
    }

    public List<SongDto> viewAllUserSongs (String username){
      List<SongEntity> songEntityList = songRepository.findByUserName(username, SongStatus.DELETED);
 if(songEntityList.isEmpty()){
   throw new CustomException(ErrorMessage.USER_SONGS_NOT_FOUND);
 }
      return songEntityList.stream().map(x-> new SongDto(x) ).toList();
    }

}

