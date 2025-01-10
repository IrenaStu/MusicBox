package com.example.musicBox.repository;

import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long > {

    @Query("""
            Select u from UserEntity u where u.username = :username 
            """)
    Optional<UserEntity> findByUserName(@Param("username") String username);

    boolean existsByUsername(String username);

  /*  @Query("""
    SELECT u FROM UserEntity u 
    WHERE u.status IN :statuses
   """)
     Page<UserEntity> findAll(@Param("statuses") List<UserStatus> statuses, Pageable pageable);
     */


    @Query("""
    SELECT u FROM UserEntity u
    WHERE u.status != :excludedStatus
""")
    Page<UserEntity> findAll(@Param("excludedStatus") UserStatus excludedStatus, Pageable pageable);


}
