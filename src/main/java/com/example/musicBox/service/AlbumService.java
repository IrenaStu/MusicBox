package com.example.musicBox.service;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.AlbumDto;
import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.mapper.AlbumMapper;
import com.example.musicBox.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    @Autowired
    private final AlbumRepository albumRepository;



    public Page<AlbumDto> getAllAlbums(Integer page, Integer size){
        return albumRepository.findAll(AlbumStatus.DELETE, PageRequest.of(page, size)).map(AlbumDto::new);
    }


    public AlbumDto getAlbum(Long albumId){
        AlbumEntity album = albumRepository.findById(albumId).orElseThrow(()->new CustomException(ErrorMessage.ALBUM_NOT_FOUND));
        return AlbumMapper.albumDto(album);
    }
    public List<AlbumDto> viewArtistsAlbums(String username) {

        List<AlbumEntity> albumEntityList = albumRepository.findAllActiveStatus(AlbumStatus.DELETE);

        return albumEntityList.stream()
                .filter(album -> album.getSongsInAlbum().stream()
                        .allMatch(song -> song.getUserUploader()
                                .getUsername().equals(username)))
                .map(AlbumMapper::albumDto) // Using method reference here
                .toList();
    }



}
