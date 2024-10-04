package identity_service.demo.service.impl;

import identity_service.demo.entity.User;
import identity_service.demo.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.jwt.signerKey}")
    private String SECRET_KEY;
    //private final long JWT_EXPIRATION_TIME = 604800000L;// 7 day

    public String generateToken(User user) {
        Date now = new Date();
        //Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TIME);
        Date expirationDate = new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(user.getUserName())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSecretKey(),Jwts.SIG.HS512)
                .claim("scope",buildScope(user))
                .compact();
    }

    // Liet ke cac role trong 1 user
    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        //if (!CollectionUtils.isEmpty(user.getRoles())) {
            //user.getRoles().forEach(scopeJoiner::add);
        //}
        return scopeJoiner.toString();
    }

    private SecretKey getSecretKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

        //MacAlgorithm macAlgorithm = Jwts.SIG.HS512;
        //return macAlgorithm.key().build();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }
}
