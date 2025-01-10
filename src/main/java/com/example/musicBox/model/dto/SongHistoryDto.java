package com.example.musicBox.model.dto;

import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.SongHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SongHistoryDto {

    private Long userListenerId;
    private String listenerName;
    private Map<Long, Long> songListenCountMap; // Map to hold songId as key and listen count as value

    public SongHistoryDto(SongHistory songHistory) {
        this.userListenerId = songHistory.getListener().getId();
        this.listenerName = songHistory.getListener().getUsername();
        this.songListenCountMap = new HashMap<>();
    }

    // Method to add songId and listenCount to the map
    public void addSongListenCount(Long songId, Long listenCount) {
        songListenCountMap.put(songId, listenCount);
    }
}

