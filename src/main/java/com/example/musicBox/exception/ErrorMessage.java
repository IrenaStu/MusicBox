package com.example.musicBox.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {

    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST("Username already exists.", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT("Password or Username incorrect", HttpStatus.UNAUTHORIZED),
    USERNAME_OR_PASSWORD_INCORRECT("Username or password incorrect", HttpStatus.UNAUTHORIZED),
    USER_ACCOUNT_INACTIVE("Your account is currently inactive. Please contact support for assistance.", HttpStatus.FORBIDDEN),
    ADMIN_PROFILE_IMMUTABLE("Admin profile cannot be modified.", HttpStatus.FORBIDDEN),
    SONG_NOT_FOUND(" such song not found", HttpStatus.NOT_FOUND),
     USER_SONGS_NOT_FOUND("This Authors songs are not in the list", HttpStatus.NOT_FOUND),
    SONGS_NOT_FOUND("Song with such name is not in the List", HttpStatus.NOT_FOUND),
    ALBUM_NOT_FOUND(" such Album not found", HttpStatus.NOT_FOUND),
    PLAY_LIST_NOT_FOUND(" such PlayList not found", HttpStatus.NOT_FOUND),
    SONG_HISTORY_NOT_FOUND(" This song has not been played by user", HttpStatus.NOT_FOUND),
    SONG_ALREADY_IN_ALBUM("Username already exists.", HttpStatus.CONFLICT),
    SONG_NOT_IN_ALBUM(" such song not found in album", HttpStatus.NOT_FOUND),
    ROLE_NOT_ALLOWED("Available roles USER or ARTIST", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(" You need to Login", HttpStatus.FORBIDDEN),
    NOT_ALLOWED("You do not have permission", HttpStatus.FORBIDDEN);


    private final String message;
    private final HttpStatus httpStatus;

    ErrorMessage(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
