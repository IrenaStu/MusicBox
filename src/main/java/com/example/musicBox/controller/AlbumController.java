package com.example.musicBox.controller;

import com.example.musicBox.facade.AlbumFacade;
import com.example.musicBox.model.dto.AlbumDto;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.param.AlbumParam;
import com.example.musicBox.service.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    @Autowired
    private final AlbumFacade albumFacade;
    @Autowired
    private final AlbumService albumService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public ResponseEntity<String> createAlbum (@Valid @RequestBody AlbumParam albumParam){
        return ResponseEntity.ok(albumFacade.createAlbum(albumParam));
    }


    @PutMapping("/{albumId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public ResponseEntity<String> updateAlbumName (@PathVariable("albumId") Long albumId,
                                                   @RequestParam("albumNewName") String albumNewName){
        return ResponseEntity.ok(albumFacade.updateAlbum(albumId, albumNewName));
    }
    @PutMapping("/albums/{albumId}/status/{newStatus}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeAlbumStatus(@PathVariable("albumId") Long albumId,
                                                    @PathVariable("newStatus") AlbumStatus newStatus) {
        albumFacade.updateAlbumStatus(albumId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("Album status updated successfully.");
    }


    @PutMapping("/{albumId}/{songId}/{newSongId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public  ResponseEntity<String> replaceSongInAlbum(@PathVariable("albumId") Long albumId,
                                                      @PathVariable("songId") Long songId,
                                                      @PathVariable("newSongId") Long newSongId){
        return ResponseEntity.ok(albumFacade.replaceSongInAlbum(albumId,songId,newSongId));
    }

    @PutMapping("/add/{albumId}/{songId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public  ResponseEntity<String> addSong(@PathVariable("albumId") Long albumId,
                                           @PathVariable("songId") Long songId){
        return ResponseEntity.ok(albumFacade.addSongToAlbum(albumId, songId));
    }
    @PutMapping("/remove/{albumId}/{songId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public  ResponseEntity<String> removeSong(@PathVariable("albumId") Long albumId,
                                           @PathVariable("songId") Long songId){
        return ResponseEntity.ok(albumFacade.removeSongFromAlbum(albumId, songId));
    }
    @GetMapping("/viewAll")

    public ResponseEntity<Page<AlbumDto>> viewAll(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size){
        return ResponseEntity.ok(albumService.getAllAlbums(page,size));
    }
    @GetMapping("/viewAlbum/{albumId}")
    public ResponseEntity<AlbumDto> viewAlbum(@PathVariable("albumId") Long albumId){
        return ResponseEntity.ok(albumService.getAlbum(albumId));
    }

@GetMapping("/users/view")
public ResponseEntity<List<AlbumDto>> viewUsersAlbums (@RequestParam("artistName")String artistName){
        return ResponseEntity.ok(albumService.viewArtistsAlbums(artistName));
}

    @DeleteMapping("/delete/{albumId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")

    public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") Long albumId){
        albumFacade.delete(albumId);
        return ResponseEntity.noContent().build();
    }
}
