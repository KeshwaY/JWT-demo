package me.damian.ciepiela.jwtdemo.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserPutDto {

    private String username;
    private String password;
    private Collection<String> roles;

}
