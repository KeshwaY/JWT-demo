package me.damian.ciepiela.jwtdemo.security.token.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.damian.ciepiela.jwtdemo.security.details.UserDetailsImpl;
import me.damian.ciepiela.jwtdemo.security.token.TokenCreator;
import me.damian.ciepiela.jwtdemo.security.token.dto.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenCreator tokenCreator;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenCreator tokenCreator) {
        super(authenticationManager);
        this.tokenCreator = tokenCreator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        String accessToken = tokenCreator.createAccessToken(user, request.getRequestURL().toString());
        String refreshToken = tokenCreator.createRefreshToken(user, request.getRequestURL().toString());
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokenResponse);
    }

}