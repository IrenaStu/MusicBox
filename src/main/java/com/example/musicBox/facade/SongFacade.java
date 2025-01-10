package com.example.musicBox.facade;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.SongDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.MusicGenre;
import com.example.musicBox.model.enums.SongStatus;
import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.mapper.SongMapper;
import com.example.musicBox.model.param.SongParam;
import com.example.musicBox.repository.SongRepository;
import com.example.musicBox.repository.UserRepository;
import com.example.musicBox.service.AuthService;
import com.example.musicBox.service.SongHistoryService;
import com.example.musicBox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SongFacade {
 @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private  final UserRepository userRepository;
    @Autowired
    private  final SongHistoryService songHistoryService;


    public String uploadSong (SongParam songParam, MusicGenre genre){
        Long principal = authService.getPrincipalDatabaseId();
        UserEntity user = userService.findByUserId(principal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        SongEntity songToUpload = SongMapper.toSongEntity(songParam);
        songToUpload.setUserUploader(user);
        songToUpload.setGenre(genre);

        SongEntity savedSong = songRepository.save(songToUpload);
        System.out.println("Uploaded song name: " + savedSong.getSongName());
        return savedSong.getSongName();
    }

    public String updateSong (Long songId, SongParam songParam){
        Long principal = AuthService.getPrincipalDatabaseId();
       UserEntity user = userRepository.findById(principal)
               .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
       SongEntity songToUpdate = songRepository.findById(songId)
               .orElseThrow(()-> new CustomException(ErrorMessage.SONGS_NOT_FOUND));
        if(!(Objects.equals(songToUpdate.getUserUploader().getId(), principal) || user.getRole() == UserRole.ADMIN)){
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
       songToUpdate.setSongName(songParam.getSongName());
       songToUpdate.setUrl(songParam.getUrl());
       return songRepository.save(songToUpdate).getSongName();
    }


    public SongDto getSong (Long songId){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity userListener = userService.findByUserId(principal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(()-> new CustomException(ErrorMessage.SONG_NOT_FOUND));

           songHistoryService.trackSongHistory(userListener,song);

        return  SongMapper.ToSongDto(song);

    }

    public void delete (Long songId) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        SongEntity songToDelete = songRepository.findById(songId)
                .orElseThrow(()-> new CustomException(ErrorMessage.SONGS_NOT_FOUND));
        if(!(Objects.equals(songToDelete.getUserUploader().getId(), principal) || user.getRole() == UserRole.ADMIN)){
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        songToDelete.setStatus(SongStatus.DELETED);
        System.out.println("song has been deleted");
        songRepository.save(songToDelete);

    }



}
