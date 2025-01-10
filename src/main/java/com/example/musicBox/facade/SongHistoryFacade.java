package com.example.musicBox.facade;

import com.example.musicBox.aspect.MyLogger;
import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.SongHistoryDto;
import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.SongHistory;
import com.example.musicBox.repository.SongHistoryRepository;
import com.example.musicBox.repository.SongRepository;
import com.example.musicBox.repository.UserRepository;
import com.example.musicBox.service.SongHistoryService;
import com.example.musicBox.service.UserService;
import com.example.musicBox.utilites.WeekUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongHistoryFacade {

    @Autowired
    private final UserService userService;
    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private  final UserRepository userRepository;
    @Autowired
    private  final SongHistoryService songHistoryService;
    @Autowired
    private final SongHistoryRepository songHistoryRepository;


    @Scheduled(cron = " * 04 18 * * *")
@Transactional
public void songHistoryWeeklyStatistic() {
    LocalDateTime startOfWeek = WeekUtils.getStartOfWeek();
    LocalDateTime endOfWeek = WeekUtils.getEndOfWeek();
    List<SongHistory> songHistories = songHistoryRepository.findAll();
    if (songHistories.isEmpty()) {
        System.out.println("songs have not been played in this week");
        throw new CustomException(ErrorMessage.SONG_HISTORY_NOT_FOUND);
    }
    for (SongHistory songHistory : songHistories) {
        Map<Long,Long> weeklyStatistics = new HashMap<>();
        for (Map.Entry<Long, List<LocalDateTime>> entry : songHistory.getTimestampsForSong().entrySet()) {
            Long songId = entry.getKey();
            List<LocalDateTime> timeStamps = entry.getValue();
            List<LocalDateTime> filteredTimeStamps = timeStamps.stream()
                    .filter(x -> !x.isBefore(startOfWeek) && !x.isAfter(endOfWeek))
                    .collect(Collectors.toList());
            Long listenCount = (long) filteredTimeStamps.size();

            if (listenCount > 0) {
          weeklyStatistics.put(songId, listenCount);
            }
            System.out.println("user: "+songHistory.getListener().getUsername()+" weekly statistic: ");


            }
        for (Map.Entry<Long,Long> stat: weeklyStatistics.entrySet()){
            Long songId = stat.getKey();
            Long listenCount = stat.getValue();
                    SongEntity song = songRepository.findById(songId) .orElseThrow(()->new CustomException(ErrorMessage.SONG_NOT_FOUND));
            String songName = song.getSongName();

            System.out.println( "song ID :"+songId+" Song Name: "+songName+"" + " Weekly played: "+listenCount);
        }
    }
}



    public List<SongHistoryDto> getFilteredSongHistories() {
        // Get the start and end of the current week
        LocalDateTime startOfWeek = WeekUtils.getStartOfWeek();
        LocalDateTime endOfWeek = WeekUtils.getEndOfWeek();

        // Retrieve SongHistory objects from the repository
        List<SongHistory> songHistories = songHistoryRepository.findAll();  // Retrieve all or filter by user/listener as needed

        if (songHistories.isEmpty()) {
            throw new CustomException(ErrorMessage.SONG_HISTORY_NOT_FOUND);
        }

        // Initialize the list for SongHistoryDto objects
        List<SongHistoryDto> songHistoryDtoList = new ArrayList<>();

        // Iterate through each SongHistory object
        for (SongHistory songHistory : songHistories) {
            // Create a new SongHistoryDto for each listener
            SongHistoryDto songHistoryDto = new SongHistoryDto(songHistory);

            // Iterate through the timestampsForSong map
            for (Map.Entry<Long, List<LocalDateTime>> entry : songHistory.getTimestampsForSong().entrySet()) {
                Long songId = entry.getKey();
                List<LocalDateTime> timeStamps = entry.getValue();

                // Filter timestamps that fall within the current week
                List<LocalDateTime> filteredTimeStamps = timeStamps.stream()
                        .filter(x -> !x.isBefore(startOfWeek) && !x.isAfter(endOfWeek))
                        .collect(Collectors.toList());

                // If there are any filtered timestamps, process the data
                if (!filteredTimeStamps.isEmpty()) {
                    Long listenCount = (long) filteredTimeStamps.size();

                    // Add the songId and listenCount to the SongHistoryDto
                    songHistoryDto.addSongListenCount(songId, listenCount);
                }
            }

            // Add the SongHistoryDto to the list if it contains song data
            if (!songHistoryDto.getSongListenCountMap().isEmpty()) {
                songHistoryDtoList.add(songHistoryDto);
            }
        }

        // Return the list of SongHistoryDto
        return songHistoryDtoList;
    }


}

