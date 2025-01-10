package com.example.musicBox.controller;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.facade.SongHistoryFacade;
import com.example.musicBox.model.dto.SongHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/song-history")
@RequiredArgsConstructor
public class SongHistoryController {
    @Autowired
    private final SongHistoryFacade songHistoryFacade;




    @GetMapping("/filtered")
    public ResponseEntity<List<SongHistoryDto>> getFilteredSongHistories() {
        try {
            List<SongHistoryDto> songHistoryDtoList = songHistoryFacade.getFilteredSongHistories();
            return ResponseEntity.ok(songHistoryDtoList);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
