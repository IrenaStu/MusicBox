package com.example.musicBox.service;

import com.example.musicBox.model.dto.SongHistoryDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.SongHistory;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.mapper.SongHistoryMapper;
import com.example.musicBox.repository.SongHistoryRepository;
import com.example.musicBox.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;



@Service
@RequiredArgsConstructor
public class SongHistoryService {
    @Autowired
 private final SongHistoryRepository songHistoryRepository;
    public void trackSongHistory(UserEntity user, SongEntity song) {
        Optional<SongHistory> existingSongHistoryOptional = songHistoryRepository.findByListener(user);

        SongHistory songHistory;

        if (existingSongHistoryOptional.isEmpty()) {
            // Create a new SongHistory entry
            songHistory = new SongHistory();
            songHistory.setListener(user);

            // Initialize the map with the current timestamp for the song
            Map<Long, List<LocalDateTime>> timestampsForSong = new HashMap<>();
            timestampsForSong.put(song.getId(), new ArrayList<>(Collections.singletonList(LocalDateTime.now())));
            songHistory.setTimestampsForSong(timestampsForSong);
        } else {
            // Update existing SongHistory entry
            songHistory = existingSongHistoryOptional.get();
            Map<Long, List<LocalDateTime>> timestampsForSong = songHistory.getTimestampsForSong();

            if (timestampsForSong == null) {
                timestampsForSong = new HashMap<>();
            }

            // Merge the timestamp for the song
            timestampsForSong.merge(song.getId(),
                    new ArrayList<>(Collections.singletonList(LocalDateTime.now())), // New timestamp
                    (existingTimestamps, newTimestamps) -> {
                        existingTimestamps.addAll(newTimestamps); // Add new timestamp to the existing list
                        return existingTimestamps;
                    });

            songHistory.setTimestampsForSong(timestampsForSong);
        }

        // Save the updated SongHistory to the database
        songHistoryRepository.save(songHistory);
    }











    public void saveSongHistory(SongHistory songHistory) {
        songHistoryRepository.save(songHistory);
    }


  
}
