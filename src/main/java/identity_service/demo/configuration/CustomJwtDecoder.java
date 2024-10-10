package identity_service.demo.configuration;

import identity_service.demo.entity.InvalidToken;
import identity_service.demo.repository.InvalidTokenRepository;
import identity_service.demo.service.impl.JwtTokenServiceImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${spring.jwt.signerKey}")
    private String secretKey;

    private final JwtTokenServiceImpl jwtTokenService;
    private final InvalidTokenRepository invalidTokenRepository;

    @Override
    public Jwt decode(String token) throws JwtException {

        String jwtId = jwtTokenService.extractAllToken(token).getId();

        try {
            boolean verifyToken = jwtTokenService.validateToken(token);
            if (!verifyToken) {
                throw new JwtException("Invalid token");
            }
        } catch (ParseException e){
            throw new JwtException(e.getMessage());
        }

        Optional<InvalidToken> invalidToken = invalidTokenRepository.findById(jwtId);

        if (invalidToken.isEmpty()) {
            return NimbusJwtDecoder
                .withSecretKey(getSecretKey())
                .macAlgorithm(MacAlgorithm.HS512)
                .build()
                .decode(token);
        }else {
            throw new JwtException("Invalid token");
        }
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
