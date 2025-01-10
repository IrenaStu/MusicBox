package com.example.musicBox.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "song_history")
public class SongHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity listener;

    @ElementCollection
    @CollectionTable(
            name = "song_listen_timestamps",
            joinColumns = @JoinColumn(name = "song_history_id")
    )
    @MapKeyColumn(name = "song_id") // Song ID as key in the map
    @Column(name = "timestamp") // Store timestamps
    private Map<Long, List<LocalDateTime>> timestampsForSong = new HashMap<>();


}


