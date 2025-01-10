package com.example.musicBox.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomAuthentication  implements Authentication {

    private final String role;
  private final  String userName;
  private final Long userid;
  private  boolean authenticated;

  public CustomAuthentication(String role, String username, Long userid){
      this.role= role;
      this.userName = username;
      this.userid = userid;
      this.authenticated = true;
  }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

      return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userid;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
     this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userName;
    }
}
