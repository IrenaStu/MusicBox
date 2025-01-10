package com.example.musicBox.service;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.mapper.UserMapper;
import com.example.musicBox.model.param.RegisterParam;
import com.example.musicBox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
     private UserRepository userRepository;


     public UserEntity  findUserByUserName(String username ){
         return userRepository.findByUserName(username)
                 .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
     }

    public  Page<UserDto> viewAll(Integer page, Integer size){

         Page<UserEntity> userEntityPage = userRepository.findAll(UserStatus.DELETED,(PageRequest.of(page,size)) );
         return userEntityPage.map( UserDto::new);
   }

    public Optional<UserEntity> findByUserId(Long userId) {

        return  userRepository.findById(userId);
    }



    // Update the user's status
    public void updateUserStatus(Long userId, UserStatus newStatus) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getStatus().equals(newStatus)) {

            user.setStatus(newStatus);
            userRepository.save(user);
        }

    }




}
