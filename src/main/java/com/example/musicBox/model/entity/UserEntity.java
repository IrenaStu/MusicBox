package com.example.musicBox.model.entity;

import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity")
public class UserEntity  extends TableDates{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "emil")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "userUploader")
     private Set<SongEntity> uploadedSongs;

    @OneToMany(mappedBy = "listCreatorUser")
    private Set<PlayListEntity> usersPlayList;


    @OneToOne(mappedBy = "listener")
    private SongHistory songHistory;


}


