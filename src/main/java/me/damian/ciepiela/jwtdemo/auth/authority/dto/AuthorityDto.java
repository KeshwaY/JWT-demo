package me.damian.ciepiela.jwtdemo.auth.authority.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthorityDto {
    @NotBlank
    private String name;
}
