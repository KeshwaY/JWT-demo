package me.damian.ciepiela.jwtdemo.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserGetDto {

    private String username;
    private Collection<String> roles;

}
