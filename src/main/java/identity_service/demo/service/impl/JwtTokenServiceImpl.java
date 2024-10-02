package identity_service.demo.service.impl;

import identity_service.demo.service.JwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.jwt.signerKey}")
    private String SECRET_KEY;
    private final long JWT_EXPIRATION_TIME = 604800000L;

    public String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .header().type("JWT").and()
                .subject(username)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSecretKey(),Jwts.SIG.HS512)
                .compact();

    }

    private final SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
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
