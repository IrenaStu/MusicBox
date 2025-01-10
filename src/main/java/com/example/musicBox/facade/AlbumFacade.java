package com.example.musicBox.facade;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.mapper.AlbumMapper;
import com.example.musicBox.model.param.AlbumParam;
import com.example.musicBox.repository.AlbumRepository;
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
public class AlbumFacade {

    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepository;


    public String createAlbum(AlbumParam albumParam) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

        Set<Long> songIdsForAlbum = albumParam.getSongIds();
        if (songIdsForAlbum.isEmpty()) {
            throw new CustomException(ErrorMessage.SONGS_NOT_FOUND);
        }

        List<SongEntity> songsList = songRepository.findAllById(songIdsForAlbum);
        for (SongEntity song : songsList) {
            if (!song.getUserUploader().getId().equals(principal)) {
                throw new CustomException(ErrorMessage.NOT_ALLOWED);
            }
        }
        if (songIdsForAlbum.size() != songsList.size()) {
            throw new CustomException(ErrorMessage.SONGS_NOT_FOUND);
        }
        Set<SongEntity> songsForAlbum = new HashSet<>(songsList);
        AlbumEntity album = AlbumMapper.toAlbumEntity(albumParam);
        album.setSongsInAlbum(songsForAlbum);

        return albumRepository.save(album).getAlbumName();

    }

    public String updateAlbum(Long albumId, String newAlbumName) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        AlbumEntity albumToUpdate = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));
        for (SongEntity song : albumToUpdate.getSongsInAlbum()) {
            if (!song.getUserUploader().getId().equals(principal)) {
                throw new CustomException(ErrorMessage.NOT_ALLOWED);
            }
        }

        albumToUpdate.setAlbumName(newAlbumName);
        return " new album name: " + albumRepository.save(albumToUpdate).getAlbumName();

    }
    public void updateAlbumStatus(Long albumId, AlbumStatus newStatus) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));
        for (SongEntity song : album.getSongsInAlbum()) {
            if (!song.getUserUploader().getId().equals(principal)) {
                throw new CustomException(ErrorMessage.NOT_ALLOWED);
            }
        }
        if (!album.getStatus().equals(newStatus)) {

            album.setStatus(newStatus);
            albumRepository.save(album);
        }
    }
    public String replaceSongInAlbum(Long albumId, Long oldSongId, Long newSongId) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));

        SongEntity oldSong = songRepository.findById(oldSongId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));
        if (!oldSong.getUserUploader().getId().equals(principal)) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        SongEntity newSong = songRepository.findById(newSongId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));
        if (!newSong.getUserUploader().getId().equals(principal)) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        if (!album.getSongsInAlbum().contains(oldSong)) {
            throw new CustomException(ErrorMessage.SONG_NOT_IN_ALBUM);
        }

        album.getSongsInAlbum().remove(oldSong);
        album.getSongsInAlbum().add(newSong);

        return "Song: " + oldSong.getSongName() + "replaced successfully with: " + newSong.getSongName() + " in album: " + albumRepository.save(album).getAlbumName();
    }

    public String addSongToAlbum(Long albumId, Long songId) {
        // Get the current user's principal ID
        Long principal = AuthService.getPrincipalDatabaseId();


        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

        // Fetch the album by ID
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));

        // Fetch the song by ID
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));

        // Validate that the current user is the uploader of the song they are adding
        if (!song.getUserUploader().getId().equals(principal)) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }

        // Validate that the current user is the uploader of all existing songs in the album
        boolean isUploaderOfAllSongsInAlbum = album.getSongsInAlbum().stream()
                .allMatch(songInAlbum -> songInAlbum.getUserUploader().getId().equals(principal));

        if (!isUploaderOfAllSongsInAlbum) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }

        // Check if the song is already in the album
        if (album.getSongsInAlbum().contains(song)) {
            throw new CustomException(ErrorMessage.SONG_ALREADY_IN_ALBUM);
        }

        // Add the new song to the album
        album.getSongsInAlbum().add(song);

        // Save the updated album and return the result
        return "Song added successfully to: " + albumRepository.save(album).getAlbumName();
    }

    public String removeSongFromAlbum(Long albumId, Long songId) {
        Long principal = AuthService.getPrincipalDatabaseId();


        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));

        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new CustomException(ErrorMessage.SONG_NOT_FOUND));

        if (!song.getUserUploader().getId().equals(principal)) {
            throw new CustomException(ErrorMessage.NOT_ALLOWED);
        }
        if (!album.getSongsInAlbum().contains(song)) {
            throw new CustomException(ErrorMessage.SONG_NOT_IN_ALBUM);
        }

        album.getSongsInAlbum().remove(song);
        albumRepository.save(album);
        return "Song removed successfully!";
    }

    public void delete(Long albumId) {
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        AlbumEntity albumToDelete = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorMessage.ALBUM_NOT_FOUND));
        for (SongEntity song : albumToDelete.getSongsInAlbum()) {
            if (!song.getUserUploader().getId().equals(principal)) {
                throw new CustomException(ErrorMessage.NOT_ALLOWED);
            }
            albumToDelete.setStatus(AlbumStatus.DELETE);
            albumRepository.save(albumToDelete);
            System.out.println("Album has been deleted");
        }


    }
}
