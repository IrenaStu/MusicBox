package com.example.musicBox.model.entity;

import com.example.musicBox.model.enums.PlayListStatus;
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
@Table(name = "play_list_entity")
public class PlayListEntity   extends TableDates{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "play_list_name")
    private String playListName;

    @Column(name = "play_list_status")
    @Enumerated(value = EnumType.STRING)
    private PlayListStatus status;

    @ManyToMany(mappedBy = "playLists")
    private Set<SongEntity> songsInPlayList;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity listCreatorUser;
}
