package me.damian.ciepiela.jwtdemo.auth.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public class RolePostDto {
    @NotBlank
    private String name;

    @NotNull
    private Collection<String> authorities;
}
