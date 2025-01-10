package com.example.musicBox.model.dto;

import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.enums.MusicGenre;
import com.example.musicBox.model.enums.SongStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SongDto {
    private  long id;
    private String songName;
    private String url;
    private SongStatus songStatus;
    private MusicGenre  genre;
    private String songAuthor;


public  SongDto(SongEntity song){
    this.id = song.getId();
    this.songName = song.getSongName();
    this.url =song.getUrl();
    this.songStatus = song.getStatus();
    this.genre= song.getGenre();
    this.songAuthor = song.getUserUploader().getUsername();
}
}
