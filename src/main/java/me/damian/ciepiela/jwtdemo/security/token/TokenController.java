package me.damian.ciepiela.jwtdemo.security.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.damian.ciepiela.jwtdemo.auth.user.User;
import me.damian.ciepiela.jwtdemo.auth.user.UserService;
import me.damian.ciepiela.jwtdemo.security.token.dto.AuthenticationErrorResponse;
import me.damian.ciepiela.jwtdemo.security.token.dto.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenCreator tokenCreator;
    private final UserService userService;

    public TokenController(TokenCreator tokenCreator, UserService userService) {
        this.tokenCreator = tokenCreator;
        this.userService = userService;
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            try {
                DecodedJWT decodedToken = tokenCreator.verifyToken(refreshToken);
                String username = decodedToken.getSubject();
                User user = userService.getRawUser(username);
                String accessToken = tokenCreator.createAccessToken(user, request.getRequestURL().toString());
                TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokenResponse);
            } catch (Exception e) {
                response.setHeader(HttpHeaders.WARNING, e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                AuthenticationErrorResponse responseError = new AuthenticationErrorResponse(e.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), responseError);
            }
        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }

}
