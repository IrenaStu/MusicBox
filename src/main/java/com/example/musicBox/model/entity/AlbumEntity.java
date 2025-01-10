package com.example.musicBox.model.entity;

import com.example.musicBox.model.enums.AlbumStatus;
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
@Table(name = "album_entity")
public class AlbumEntity  extends TableDates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "album_status")
    @Enumerated(value = EnumType.STRING)
    private AlbumStatus status;


    @ManyToMany(mappedBy = "albums")
    private Set<SongEntity> songsInAlbum;
}
