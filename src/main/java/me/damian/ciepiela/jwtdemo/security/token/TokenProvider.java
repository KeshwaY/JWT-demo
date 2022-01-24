package me.damian.ciepiela.jwtdemo.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {

    @Value("${secret}")
    private String secret;

    public String createTokenWithClaims(String subject, String issuer, Date expirationDate, String claimName, List<String> claims) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .withClaim(claimName, claims)
                .sign(algorithm);
    }

    public String createToken(String subject, String issuer, Date expirationDate) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) throws Exception  {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }


}
