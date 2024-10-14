package identity_service.demo.service.impl;

import identity_service.demo.entity.User;
import identity_service.demo.repository.InvalidTokenRepository;
import identity_service.demo.repository.UserRepository;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private final InvalidTokenRepository invalidTokenRepository;

    private final UserRepository userRepository;
    @Value("${spring.jwt.signerKey}")
    private String signerKey;
    //private final long JWT_EXPIRATION_TIME = 604800000L;// 7 day

    public String generateToken(User user) {
        Date now = new Date();
        //Date expirationDate = new Date(now.getTime() + JWT_EXPIRATION_TIME);
        Date expirationDate = new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());// 1 Gio

        return Jwts.builder()
            .header().type("JWT").and()
            .subject(user.getUserName())
            .issuedAt(now)
            .expiration(expirationDate)
            .id(UUID.randomUUID().toString())
            .signWith(getSecretKey(),Jwts.SIG.HS512)
            .claim("scope",buildScope(user))
            .compact();
    }

    public String refreshToken(String token) {
        boolean tokenValid = validateToken(token);
        var invalidToken = invalidTokenRepository.findById(extractAllToken(token).getId());
        String userName = "";
        if (tokenValid && invalidToken.isEmpty()) {
            userName = getUsername(token);
        }

        User user = userRepository.findUserByUserName(userName).get();

        return generateToken(user);
    }

    // Liet ke cac role trong 1 user
    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(
                role -> {
                    scopeJoiner.add("ROLE_" + role.getRoleName());
                    if (!CollectionUtils.isEmpty(role.getPermissions())) {
                        role.getPermissions().forEach(
                            permission -> scopeJoiner.add(permission.getPermissionName())
                        );
                    }
                }
            );
        }
        return scopeJoiner.toString();
    }

    private SecretKey getSecretKey() {

        byte[] keyBytes = Decoders.BASE64.decode(signerKey);
        return Keys.hmacShaKeyFor(keyBytes);

       /* MacAlgorithm macAlgorithm = Jwts.SIG.HS512;
        return macAlgorithm.key().build();*/
    }

    public String getUsername(String token) {
        return extractAllToken(token).getSubject();
    }

    public Claims extractAllToken(String token) {
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
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
