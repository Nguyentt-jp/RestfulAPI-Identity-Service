package identity_service.demo.service.impl;

import identity_service.demo.entity.InvalidToken;
import identity_service.demo.repository.InvalidTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl {
    private final InvalidTokenRepository invalidTokenRepository;
    private final JwtTokenServiceImpl jwtTokenService;

    public InvalidToken createInvalidToken(String token) {
        Claims claims = jwtTokenService.extractAllToken(token);

        InvalidToken invalidToken = InvalidToken.builder()
            .tokenId(claims.getId())
            .expiresAt(claims.getExpiration())
            .build();

        return invalidTokenRepository.save(invalidToken);
    }
}
