package com.example.musicBox.repository;

import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.entity.SongHistory;
import com.example.musicBox.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SongHistoryRepository  extends JpaRepository<SongHistory, Long> {


    @Query("""
                Select sh from SongHistory sh
                where sh.listener = :listener
             
            """)
    Optional<SongHistory> findByListener(@Param("listener") UserEntity listener);

}

    /*    @Query("""
    Select sh from SongHistory sh
    join sh.songs s
    where sh.listener = :listener
    and s = :song
""")     */






