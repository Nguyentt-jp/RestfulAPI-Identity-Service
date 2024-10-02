package identity_service.demo.service.impl;

import identity_service.demo.dto.request.AuthenticationRequest;
import identity_service.demo.dto.request.IntrospectRequest;
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

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenServiceImpl jwtTokenService;
    
    public UserResponse authenticate(AuthenticationRequest authenRequest) {

        User existedUser = userRepository.findUserByUserName(authenRequest.getUserName());

        if (existedUser == null) {
            throw new AppException(ErrorCode.INVALID_USER_NOT_EXISTED);
        }

        if (new BCryptPasswordEncoder().matches(authenRequest.getPassword(), existedUser.getPassword())){
            UserResponse userResponse = userMapper.mapperUserToUserResponse(existedUser);
            userResponse.setToken(jwtTokenService.generateToken(existedUser.getUserName()));
            return userResponse;
        }else {
            throw new AppException(ErrorCode.INVALID_LOGIN_FAILED);
        }
    }

    public UserResponse authenticateWithToken(IntrospectRequest authenRequest) {
        String name = jwtTokenService.getUsername(authenRequest.getToken());
        User user = userRepository.findUserByUserName(name);

        UserResponse userResponse = userMapper.mapperUserToUserResponse(user);
        userResponse.setToken(authenRequest.getToken());
        return userResponse;
    }
}
