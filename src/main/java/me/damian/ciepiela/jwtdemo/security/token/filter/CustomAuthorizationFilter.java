package me.damian.ciepiela.jwtdemo.security.token.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.damian.ciepiela.jwtdemo.security.token.TokenCreator;
import me.damian.ciepiela.jwtdemo.security.token.dto.AuthenticationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final TokenCreator tokenCreator;
    private final String loginPath;
    private final String refreshTokenPath;

    public CustomAuthorizationFilter(TokenCreator tokenCreator, String loginPath, String refreshTokenPath) {
        this.tokenCreator = tokenCreator;
        this.loginPath = loginPath;
        this.refreshTokenPath = refreshTokenPath;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(loginPath) || request.getServletPath().equals(refreshTokenPath)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                try {
                    DecodedJWT decodedToken = tokenCreator.verifyToken(token);
                    String username = decodedToken.getSubject();
                    String[] roles = decodedToken.getClaim("authorities").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(a -> authorities.add(new SimpleGrantedAuthority(a)));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setHeader(HttpHeaders.WARNING, e.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    AuthenticationErrorResponse responseError = new AuthenticationErrorResponse(e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), responseError);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
