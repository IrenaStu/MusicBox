package com.example.musicBox.controller;

import com.example.musicBox.facade.AuthFacade;
import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.param.LoginParam;
import com.example.musicBox.model.param.RegisterParam;
import com.example.musicBox.model.param.UserPasswordChangeParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/register/{choiceRole}") //anonymous
    public String singUp(@PathVariable @Schema(description = "Allowed roles: USER, ARTIST")UserRole choiceRole,
            @RequestBody @Valid RegisterParam register) {
        return authFacade.registerUser(register, choiceRole);
    }

    @PostMapping("/login")
    public  String login(@RequestBody@Valid LoginParam param){
        return  authFacade.login(param);
    }


    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UserPasswordChangeParam passwordChangeParam){
        String responseMassage = authFacade.updatePassword(passwordChangeParam);
        return ResponseEntity.ok(responseMassage);
    }
}
