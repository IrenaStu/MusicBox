package com.example.musicBox.facade;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.entity.PlayListEntity;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.PlayListStatus;
import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.mapper.PlayListMapper;
import com.example.musicBox.model.param.PlayListParam;
import com.example.musicBox.repository.AlbumRepository;
import com.example.musicBox.repository.PlayListRepository;
import com.example.musicBox.repository.SongRepository;
import com.example.musicBox.repository.UserRepository;
import com.example.musicBox.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlayListFacade {
    @Autowired
    private final PlayListRepository playListRepository;
    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepository;


    public String createPlayList(PlayListParam playListParam) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity userCreator = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

        Set<Long> playListSongId = playListParam.getListSongIds();
        if (playListSongId.isEmpty()) {
            throw new CustomException(ErrorMessage.SONGS_NOT_FOUND);
        }
        List<SongEntity> songEntityList = songRepository.findAllById(playListSongId);
        if (songEntityList.size() != playListSongId.size()) {
            throw new CustomException(ErrorMessage.SONGS_NOT_FOUND);
        }
        Set<SongEntity> songsToSetPlaylist = new HashSet<>(songEntityList);
        PlayListEntity playList = PlayListMapper.toPlayList(playListParam);
        playList.setListCreatorUser(userCreator);
        playList.setSongsInPlayList(songsToSetPlaylist);


        return "Playlist: " + playListRepository.save(playList).getPlayListName();
    }

    public String playListNameUpdate(Long playListId, String newListName) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        playList.setPlayListName(newListName);
        return "playlist updated name: " + playListRepository.save(playList).getPlayListName();

    }

    public void updatePlaylistStatus(Long playlistId, PlayListStatus newStatus) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playlistId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        if (!playList.getStatus().equals(newStatus)) {
            playList.setStatus(newStatus);
            playListRepository.save(playList);
        }

    }

    public String replaceSongInList(Long playListId, Long songId, Long newSongId){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        SongEntity oldSong = songRepository.findById(songId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));
        playList.getSongsInPlayList().remove(oldSong);
        SongEntity songToAdd =  songRepository.findById(newSongId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));
        playList.getSongsInPlayList().add(songToAdd);
        return "Song: " + oldSong.getSongName() + "replaced successfully with: " + songToAdd.getSongName() + " in album: " +playListRepository.save(playList).getPlayListName();

    }

    public String addToPlayListSong(Long playListId, Long songId){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));;
        playList.getSongsInPlayList().add(song);
        return "Song added successfully to: " +playListRepository.save(playList).getPlayListName();
    }


    public String removeFromPlayListSong(Long playListId, Long songId) {

        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        SongEntity songToRemove = songRepository.findById(songId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));
        playList.getSongsInPlayList().remove(songToRemove);
        playListRepository.save(playList);
        return songToRemove.getSongName()+"  removed successfully!";
    }

    public void deletePlayList(Long playListId){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        PlayListEntity playList = playListRepository.findById(playListId)
                .orElseThrow(() -> new CustomException(ErrorMessage.PLAY_LIST_NOT_FOUND));
        if (!playList.getListCreatorUser().getId().equals(principal) || user.getRole() != UserRole.ADMIN) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        playList.setStatus(PlayListStatus.DELETE);
        playListRepository.save(playList);
        System.out.println(" Play List has Been deleted");

    }

}
