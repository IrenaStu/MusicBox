package com.example.musicBox.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginParam {


    private String username;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;



}


