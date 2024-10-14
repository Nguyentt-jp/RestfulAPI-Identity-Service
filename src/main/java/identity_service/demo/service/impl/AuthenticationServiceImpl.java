package identity_service.demo.service.impl;

import identity_service.demo.dto.request.AuthenticationRequest;
import identity_service.demo.dto.request.ExchangeTokenRequest;
import identity_service.demo.dto.request.IntrospectRequest;
import identity_service.demo.dto.response.TokenResponse;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.Role;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.mapper.UserMapper;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.AuthenticationService;
import identity_service.demo.service.RoleService;
import identity_service.demo.service.UserService;
import identity_service.demo.service.httpclient.OutboundIdentityClient;
import identity_service.demo.service.httpclient.OutboundUserClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenServiceImpl jwtTokenService;
    private final InvalidTokenServiceImpl invalidTokenService;
    private final OutboundIdentityClient outboundIdentityClient;
    private final OutboundUserClient outboundUserClient;

    @Value("${spring.outbound.identity.client-id}")
    private String CLIENT_ID;

    @Value("${spring.outbound.identity.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.outbound.identity.redirect-uri}")
    private String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    public TokenResponse outboundAuthentication(String code) {
        var response = outboundIdentityClient.exchangeToken(
            ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build()
        );

        // Get Info User
        var userResponse = outboundUserClient.getUserInfo("json", response.getAccessToken());

        log.warn("Response: {}", response);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().roleName(identity_service.demo.entity.enums.Role.USER.name()).build());

        // Onboard User
        var user = userRepository.findUserByUserName(userResponse.getEmail()).orElseGet(
            () ->
                userRepository.save(
                    User.builder()
                        .userName(userResponse.getEmail())
                        .email(userResponse.getEmail())
                        .firstName(userResponse.getGivenName())
                        .lastName(userResponse.getFamilyName())
                        .roles(roles)
                        .build()
                )
        );

        // Create Token User
        var createToken = jwtTokenService.generateToken(user);

        return TokenResponse.builder()
            .token(createToken)
            .build();

    }

    public TokenResponse login(AuthenticationRequest authenRequest) {

        User existedUser = userRepository.findUserByUserName(authenRequest.getUserName())
            .orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_NOT_EXISTED)
            );

        if (new BCryptPasswordEncoder().matches(authenRequest.getPassword(), existedUser.getPassword())) {

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwtTokenService.generateToken(existedUser));
            return tokenResponse;
        } else {
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
        if (jwtTokenService.validateToken(authenRequest.getToken())) {
            user = userRepository.findUserByUserName(jwtTokenService.getUsername(authenRequest.getToken())).get();
        }

        return userMapper.mapperUserToUserResponse(user);
    }
}
