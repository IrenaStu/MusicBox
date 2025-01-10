package com.example.musicBox.repository;

import com.example.musicBox.model.entity.AlbumEntity;
import com.example.musicBox.model.enums.AlbumStatus;
import com.example.musicBox.model.enums.UserStatus;
import org.hibernate.query.criteria.JpaCollectionJoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository  extends JpaRepository<AlbumEntity, Long> {


    @Query("""
            select alb from AlbumEntity alb where  alb.status != :excludeStatus
             """)
    Page<AlbumEntity> findAll(@Param("excludedStatus") AlbumStatus excludedStatus, Pageable pageable);

    @Query("""
            select alb from AlbumEntity  alb where  alb.status != :excludeStatus
            """)
    List<AlbumEntity> findAllActiveStatus(@Param("excludedStatus") AlbumStatus excludedStatus);
}
