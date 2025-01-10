package com.example.musicBox.model.entity;

import com.example.musicBox.model.enums.MusicGenre;
import com.example.musicBox.model.enums.SongStatus;
import com.example.musicBox.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "song_entity")
public class SongEntity   extends TableDates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "song_name")
    private String songName;

    @Column(name = "url_column")
    private String url;

    @Column(name = "genre")
    @Enumerated(value = EnumType.STRING)
    private MusicGenre genre;

    @Column(name = "song_status")
    @Enumerated(value = EnumType.STRING)
    private SongStatus status;
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    private UserEntity userUploader;


    @ManyToMany
    @JoinTable(name = "song_play_list", joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Set<PlayListEntity> playLists;

    @ManyToMany
    @JoinTable(name = "song_album", joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))

    private Set<AlbumEntity> albums;
}
