package com.example.musicBox.controller;

import com.example.musicBox.facade.PlayListFacade;
import com.example.musicBox.model.dto.PlayListDto;
import com.example.musicBox.model.enums.PlayListStatus;
import com.example.musicBox.model.param.PlayListParam;
import com.example.musicBox.service.PlayListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlayListController {

    @Autowired
    private final PlayListFacade playListFacade;
    @Autowired
    private final PlayListService playListService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> createPlayList(@Valid @RequestBody PlayListParam playListParam) {
        return ResponseEntity.ok(playListFacade.createPlayList(playListParam));
    }

    @GetMapping("/viewAll")
    public ResponseEntity<Page<PlayListDto>> viewAllPlayLists(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        return ResponseEntity.ok(playListService.viewAllPlayLists(page, size));
    }

    @GetMapping("/view/{playListId}")
    public ResponseEntity<PlayListDto> viewPlayList(@PathVariable("playListId") Long playListId) {
        return ResponseEntity.ok(playListService.viewPlayListById(playListId));
    }

    @PutMapping("/{playListId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> updatePlayListName(@PathVariable("playListId") Long playListId,
                                                     @RequestParam("newPlayListName") String newPlayListName) {
        return ResponseEntity.ok(playListFacade.playListNameUpdate(playListId, newPlayListName));
    }

    @PutMapping("/{playListId}/status/{newStatus}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> changePlayListStatus(@PathVariable("playListId") Long playListId,
                                                       @PathVariable("newStatus") PlayListStatus newStatus) {
        playListFacade.updatePlaylistStatus(playListId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("PlayList status updated successfully.");
    }

    @PutMapping("/{playListId}/{songId}/{newSongId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> replaceSongInPlayList(@PathVariable("playListId") Long playListId,
                                                        @PathVariable("songId") Long songId,
                                                        @PathVariable("newSongId") Long newSongId) {
        return ResponseEntity.ok(playListFacade.replaceSongInList(playListId, songId, newSongId));
    }

    @PutMapping("/add/{playListId}/{songId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> addSongToPlayList(@PathVariable("playListId") Long playListId,
                                                    @PathVariable("songId") Long songId) {
        return ResponseEntity.ok(playListFacade.addToPlayListSong(playListId, songId));
    }

    @PutMapping("/remove/{playListId}/{songId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<String> removeSongFromPlayList(@PathVariable("playListId") Long playListId,
                                                         @PathVariable("songId") Long songId) {
        return ResponseEntity.ok(playListFacade.removeFromPlayListSong(playListId, songId));
    }

    @DeleteMapping("/delete/{playListId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
    public ResponseEntity<Void> deletePlayList(@PathVariable("playListId") Long playListId) {
        playListFacade.deletePlayList(playListId);
        return ResponseEntity.noContent().build();
    }
}

