package me.damian.ciepiela.jwtdemo.security.token.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AuthenticationErrorResponse {
    @JsonProperty("error_message")
    private String errorMessage;
}
