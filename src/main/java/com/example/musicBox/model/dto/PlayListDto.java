package com.example.musicBox.model.dto;

import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.entity.PlayListEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.enums.PlayListStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PlayListDto {

    private  Long playListId;
    private PlayListStatus Status;
    private  String playListName;
    private String userCreator;
    private Long creatorId;
    private Set<SongDto> songsInPlayList;


    public PlayListDto(PlayListEntity playList) {
        this.playListId =playList.getId();
        this.Status = playList.getStatus();
        this.playListName = playList.getPlayListName();
        this.userCreator = playList.getListCreatorUser().getUsername();
        this.creatorId=playList.getListCreatorUser().getId();
        this.songsInPlayList = playList.getSongsInPlayList().stream().map(SongDto::new).collect(Collectors.toSet());
    }
}
