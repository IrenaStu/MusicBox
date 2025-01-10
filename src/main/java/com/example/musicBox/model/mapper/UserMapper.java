package com.example.musicBox.model.mapper;

import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.param.RegisterParam;

import java.util.Optional;

public class UserMapper {


    public  static UserEntity toUserEntity (RegisterParam registerParam){
        UserEntity user = new UserEntity();
        user.setUsername(registerParam.getUsername());
        user.setFirstName(registerParam.getFirstName());
        user.setLastName(registerParam.getLastName());
        user.setEmail(registerParam.getEmail());
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }


    public static UserDto toUserDto(UserEntity userEntity) {
        return new UserDto(userEntity);
    }

}
