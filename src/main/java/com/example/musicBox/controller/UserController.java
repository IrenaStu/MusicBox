package com.example.musicBox.controller;

import com.example.musicBox.facade.UserFacade;
import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.param.RegisterParam;
import com.example.musicBox.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
@Autowired
    private  final UserService userService;
 private final UserFacade userFacade;

@GetMapping
@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")
public ResponseEntity<UserDto> userViewItsInfo(){
    return ResponseEntity.ok(userFacade.userViewItsInformation());
}

@PutMapping
@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ARTIST')")

public ResponseEntity<String> updateItself(@Valid@RequestBody RegisterParam registerParam){
    return ResponseEntity.ok(userFacade.updateUserInformation(registerParam));
}


    @DeleteMapping ("/deleteUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(){
        userFacade.delete();
        return ResponseEntity.noContent().build();

    }
}
