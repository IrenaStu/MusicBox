package com.example.musicBox.facade;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.mapper.UserMapper;
import com.example.musicBox.model.param.RegisterParam;
import com.example.musicBox.repository.UserRepository;
import com.example.musicBox.service.AuthService;
import com.example.musicBox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    public final UserService userService;
    public final AuthService authService;
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;






    public UserDto viewUser(Long userId){
        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        return UserMapper.toUserDto(user);
    }


    public UserDto userViewItsInformation(){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        return UserMapper.toUserDto(user);
    }


    public String updateUserInformation(RegisterParam registerParam){
        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity userToUpdate = userRepository.findById(principal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        userToUpdate.setUsername(registerParam.getUsername());
        userToUpdate.setFirstName(registerParam.getFirstName());
        userToUpdate.setLastName(userToUpdate.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        userToUpdate.setEmail(registerParam.getEmail());
        return "User "+userRepository.save(userToUpdate).getUsername() +"Has been successfully updated";

    }
     public void  delete(){
        Long userPrincipal = AuthService.getPrincipalDatabaseId();
        UserEntity userToDelete = userRepository.findById(userPrincipal)
                .orElseThrow(()-> new CustomException(ErrorMessage.USER_NOT_FOUND));
        userToDelete.setStatus(UserStatus.DELETED);
        userRepository.save(userToDelete);
         System.out.println("User has been deleted");
     }

}
