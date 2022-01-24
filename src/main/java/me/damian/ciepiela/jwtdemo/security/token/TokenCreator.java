package me.damian.ciepiela.jwtdemo.security.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.damian.ciepiela.jwtdemo.auth.authority.Authority;
import me.damian.ciepiela.jwtdemo.auth.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data @AllArgsConstructor
public class TokenCreator {

    private final TokenProvider tokenProvider;

    public String createAccessToken(UserDetails user, String issuer) {
        Date tokenExpirationDate = Date.from(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.UTC));
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableList());
        return tokenProvider.createTokenWithClaims(user.getUsername(), issuer, tokenExpirationDate, "authorities", authorities);
    }

    public String createAccessToken(User user, String issuer) {
        Date tokenExpirationDate = Date.from(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.UTC));
        Set<String> authorities = new HashSet<>();
        user.getRoles().forEach(r -> {
            authorities.add(r.getName());
            authorities.addAll(r.getAuthorities().stream()
                    .map(Authority::getName)
                    .collect(Collectors.toUnmodifiableSet())
            );
        });
        return tokenProvider.createTokenWithClaims(user.getUsername(), issuer, tokenExpirationDate, "authorities", authorities.stream().collect(Collectors.toUnmodifiableList()));
    }

    public String createRefreshToken(UserDetails user, String issuer) {
        String subject = user.getUsername();
        Date refreshTokenExpirationDate = Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC));
        return tokenProvider.createToken(subject, issuer, refreshTokenExpirationDate);
    }

    public DecodedJWT verifyToken(String token) throws Exception {
        return tokenProvider.decodeToken(token);
    }

}
