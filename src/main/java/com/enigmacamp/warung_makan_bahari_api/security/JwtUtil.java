package com.enigmacamp.warung_makan_bahari_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@Slf4j
public class JwtUtil {
    private final String jwtSecret = "sukaSukaS4y@doNk";
    private final String issuer = "Warung Makan Bahari";

    public String generateToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            return JWT.create()
                    .withIssuer(issuer)
                    .withExpiresAt(Instant.now().plusSeconds(600))
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("Error when creating token", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Boolean verifyJwtToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(issuer);
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT", e.getMessage());
            return false;
        }
    }

    public Map<String, Object> getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role"));
            return userInfo;
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT", e.getMessage());
            return null;
        }
    }
}
