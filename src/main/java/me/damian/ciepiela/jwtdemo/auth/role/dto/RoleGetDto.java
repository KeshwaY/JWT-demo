package me.damian.ciepiela.jwtdemo.auth.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleGetDto {

    private String name;
    private Collection<String> authorities;

}
