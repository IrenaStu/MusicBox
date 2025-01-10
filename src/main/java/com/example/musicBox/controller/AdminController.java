package com.example.musicBox.controller;

import com.example.musicBox.facade.UserFacade;
import com.example.musicBox.model.dto.UserDto;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private  final UserService userService;
    private final UserFacade userFacade;


    @GetMapping("/viewAll")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Page<UserDto>> viewAllUsers (@RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size){
        return  ResponseEntity.ok(userService.viewAll(page, size));
    }

    @GetMapping("/viewUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> viewUser (@RequestParam("userId") Long userId){
        return ResponseEntity.ok(userFacade.viewUser(userId)) ;
    }


    @PutMapping("/status/{userId}/{newStatus}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@PathVariable("userId") Long userId,
                                               @PathVariable("newStatus") UserStatus newStatus) {
        userService.updateUserStatus(userId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("User Status changed successfully.");
    }



}
