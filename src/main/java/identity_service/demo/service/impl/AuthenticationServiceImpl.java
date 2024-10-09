package identity_service.demo.service.impl;

import identity_service.demo.dto.request.AuthenticationRequest;
import identity_service.demo.dto.request.IntrospectRequest;
import identity_service.demo.dto.response.TokenResponse;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.mapper.UserMapper;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenServiceImpl jwtTokenService;
    private final InvalidTokenServiceImpl invalidTokenService;
    
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
