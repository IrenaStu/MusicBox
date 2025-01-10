package com.example.musicBox.repository;

import com.example.musicBox.model.entity.SongEntity;
import com.example.musicBox.model.enums.SongStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

  /*  @Query("""
        select s from SongEntity s
        where s.songName like concat('%', :songName, '%') 
          and s.status != :excludedStatus
       """) */
  @Query("""
    select s from SongEntity s
    where concat(' ', s.songName, ' ') like concat('%', :songName, '%') 
    and s.status != :excludedStatus
""")
    Page<SongEntity> findBySongName(
            @Param("songName") String songName,
            @Param("excludedStatus") SongStatus songStatus,
            Pageable pageable);



    @Query("""
            
            select s from SongEntity s
             where s.userUploader.username like concat('%', :username, '%') and s.status != :excludedStatus
             
            """)
    List<SongEntity> findByUserName (@Param("userName") String username,
                                     @Param("excludeStatus") SongStatus songStatus);

}
