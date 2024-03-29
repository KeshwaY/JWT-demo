package me.damian.ciepiela.jwtdemo.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserPostDto {

    private String username;
    private String password;
    private Set<String> roles;

}
