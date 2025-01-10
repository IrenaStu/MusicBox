package com.example.musicBox.repository;

import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.entity.PlayListEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.enums.PlayListStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListRepository  extends JpaRepository<PlayListEntity, Long> {



    @Query("""
            select pl from PlayListEntity pl where  pl.status != :excludeStatus
             """)
    Page<PlayListEntity> findAll(@Param("excludedStatus") PlayListStatus excludedStatus, Pageable pageable);

    @Query("""
          select pl from PlayListEntity pl where  pl.status != :excludeStatus
            """)
    List<PlayListEntity> findAllActiveStatus(@Param("excludedStatus")  PlayListStatus excludedStatus);
}
