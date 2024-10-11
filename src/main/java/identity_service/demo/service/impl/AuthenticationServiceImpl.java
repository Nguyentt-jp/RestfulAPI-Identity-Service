package identity_service.demo.service.impl;

import identity_service.demo.dto.request.AuthenticationRequest;
import identity_service.demo.dto.request.ExchangeTokenRequest;
import identity_service.demo.dto.request.IntrospectRequest;
import identity_service.demo.dto.response.TokenResponse;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.mapper.UserMapper;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.AuthenticationService;
import identity_service.demo.service.OutboundIdentityClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenServiceImpl jwtTokenService;
    private final InvalidTokenServiceImpl invalidTokenService;
    private final OutboundIdentityClient outboundIdentityClient;

    @Value("${spring.outbound.identity.client-id}")
    private String CLIENT_ID;

    @Value("${spring.outbound.identity.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.outbound.identity.redirect-uri}")
    private String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    public TokenResponse outboundAuthentication(String code){
        var response = outboundIdentityClient.exchangeToken(
            ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build()
        );

        log.warn("Response: {}", response);

        return TokenResponse.builder()
            .token(response.getAccessToken())
            .build();

    }
    
    public TokenResponse login(AuthenticationRequest authenRequest) {

        User existedUser = userRepository.findUserByUserName(authenRequest.getUserName());

        if (existedUser == null) {
            throw new AppException(ErrorCode.INVALID_USER_NOT_EXISTED);
        }

        if (new BCryptPasswordEncoder().matches(authenRequest.getPassword(), existedUser.getPassword())){

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwtTokenService.generateToken(existedUser));
            return tokenResponse;
        }else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public String logout(String token) {
        if (jwtTokenService.validateToken(token)) {
            invalidTokenService.createInvalidToken(token);
        }
        return "Logout!";
    }

    public UserResponse authenticateWithToken(IntrospectRequest authenRequest) {
        //String name = jwtTokenService.getUsername(authenRequest.getToken());
        User user = new User();
        if (jwtTokenService.validateToken(authenRequest.getToken())){
            user = userRepository.findUserByUserName(jwtTokenService.getUsername(authenRequest.getToken()));
        }

        return userMapper.mapperUserToUserResponse(user);
    }
}
