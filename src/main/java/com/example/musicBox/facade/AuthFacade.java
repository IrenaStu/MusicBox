package com.example.musicBox.facade;

import com.example.musicBox.exception.CustomException;
import com.example.musicBox.exception.ErrorMessage;
import com.example.musicBox.model.entity.UserEntity;
import com.example.musicBox.model.enums.UserRole;
import com.example.musicBox.model.enums.UserStatus;
import com.example.musicBox.model.mapper.UserMapper;
import com.example.musicBox.model.param.LoginParam;
import com.example.musicBox.model.param.RegisterParam;
import com.example.musicBox.model.param.UserPasswordChangeParam;
import com.example.musicBox.repository.UserRepository;
import com.example.musicBox.security.CustomAuthentication;
import com.example.musicBox.service.AuthService;
import com.example.musicBox.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthFacade {
   private final PasswordEncoder passwordEncoder;
    @Autowired
   private final UserRepository userRepository;
   private final UserService userService;
   private final AuthService authService;
   private static final String secret = "VERY-SECRET-KEY-1234567890123456";





    public String registerUser(RegisterParam registerParam, UserRole choiceRole){
   if(userRepository.existsByUsername(registerParam.getUsername())){
       throw new CustomException(ErrorMessage.USER_ALREADY_EXIST);
   }
        UserEntity userToRegister = UserMapper.toUserEntity(registerParam);
        if (!(  choiceRole== UserRole.USER || choiceRole == UserRole.ARTIST)){
            throw new CustomException(ErrorMessage.ROLE_NOT_ALLOWED);
        }

        userToRegister.setRole(choiceRole);
        userToRegister.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        return  userRepository.save(userToRegister).getUsername();
    }

    public String login(LoginParam loginParam){
   UserEntity loggingUser = userService.findUserByUserName(loginParam.getUsername());
        UserStatus status = loggingUser.getStatus();
        if(status == UserStatus.DEACTIVATED ){
            throw new CustomException(ErrorMessage.USER_ACCOUNT_INACTIVE);
        }
        if(status == UserStatus.DELETED){
            throw new CustomException(ErrorMessage.USER_NOT_FOUND);
        }
         if(!passwordEncoder.matches(loginParam.getPassword(),loggingUser.getPassword())){
             throw new CustomException(ErrorMessage.USERNAME_OR_PASSWORD_INCORRECT);
         }
        return Jwts.builder()
                .claim("username", loggingUser.getUsername())
                .claim("role", loggingUser.getRole())
                .claim("firstName",loggingUser.getFirstName())
                .claim("id", loggingUser.getId())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(Duration.ofHours(1))))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();

    }



    public Authentication authenticated (String token){
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token);
        Claims payload = claimsJws.getPayload();
        String username = payload.get("username", String.class);
        String role ="ROLE_"+payload.get("role", String.class);
        long id = payload.get("id", Long.class);
        System.out.println("Role: " +role);
        return new CustomAuthentication(role, username, id);
    }

    public String updatePassword(UserPasswordChangeParam passwordChangeParam){

        Long principal = AuthService.getPrincipalDatabaseId();
        UserEntity user = userRepository.findById(principal)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
        if(!passwordEncoder.matches(passwordChangeParam.getOldPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid password");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeParam.getNewPassword()));
        userRepository.save(user);
        return "password successfully changed ";

    }
}

