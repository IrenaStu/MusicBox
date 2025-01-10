package com.example.musicBox.controller;

import com.example.musicBox.facade.SongFacade;
import com.example.musicBox.model.dto.SongDto;
import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.enums.MusicGenre;
import com.example.musicBox.model.param.SongParam;
import com.example.musicBox.service.SongService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongFacade songFacade;

     private final SongService service;


    @PostMapping("/upload/{genre}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> uploadSong(@PathVariable("genre")MusicGenre genre,
                                             @Valid @RequestBody SongParam songParam){
        return ResponseEntity.ok(songFacade.uploadSong(songParam,genre));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> updateSong (@RequestParam Long songId,
                                              @Valid @RequestBody SongParam songParam ){
        return ResponseEntity.ok(songFacade.updateSong(songId, songParam));
    }

    @GetMapping("/viewAll")
    public ResponseEntity<Page<SongDto>> viewAllSongs(@RequestParam("songName") String songName,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size) {
        return ResponseEntity.ok(service.viewAllSongsByName(songName, page, size));
    }

    @GetMapping("/viewAll/userSongs")
    public ResponseEntity<List<SongDto>> viewUsersSongs (@RequestParam("userName")String userName){
        return ResponseEntity.ok(service.viewAllUserSongs(userName));
    }
    @GetMapping("/getSong")
    public ResponseEntity<SongDto> getSong(@RequestParam("songId") Long songId) {
        SongDto songDto = songFacade.getSong(songId);
        return ResponseEntity.ok(songDto);
    }

    @DeleteMapping ("/delete/{songId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<Void> delete (@PathVariable("songId") Long songId){
        songFacade.delete(songId);
        return ResponseEntity.noContent().build();
    }

}
