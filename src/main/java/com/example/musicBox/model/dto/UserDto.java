package com.example.musicBox.model.dto;

import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {


        private  long id;
        private String username;
        private UserRole role;
        private UserStatus status;
        private String email;


        public UserDto(UserEntity user){
            this.id = user.getId();
            this.username = user.getUsername();
            this.role = user.getRole();
            this.status = user.getStatus();
            this.email =user.getEmail();
        }

}
