package me.damian.ciepiela.jwtdemo.auth.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePutDto {
    private String name;
    private Collection<String> authorities;
}