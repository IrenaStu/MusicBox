package com.example.musicBox.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class UserPasswordChangeParam {

        @NotBlank(message = "you should provide  your password")
        private String oldPassword;
        @NotBlank(message = "you should provide  new password")
        private String newPassword;


}
