package com.example.musicBox.model.dto;

import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AlbumDto {
    private  Long albumId;
private AlbumStatus albumStatus;
    private  String albumName;
    private Set<SongDto> songsInAlbum;



    public AlbumDto(AlbumEntity album) {
        this.albumId =album.getId();
        this.albumStatus = album.getStatus();
        this.albumName = album.getAlbumName();
        this.songsInAlbum = album.getSongsInAlbum().stream().map(SongDto::new).collect(Collectors.toSet());
    }
}
