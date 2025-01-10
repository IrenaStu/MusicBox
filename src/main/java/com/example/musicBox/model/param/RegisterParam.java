package com.example.musicBox.model.param;

import com.example.musicBox.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class RegisterParam {


    private String username;

    private String firstName;

    private String lastName;


    @NotBlank
    @Email

    private String email;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

}
