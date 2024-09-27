package identity_service.demo.service.impl;

import identity_service.demo.dto.request.AuthenticationRequest;
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
    
    public UserResponse authenticate(AuthenticationRequest authenRequest) {

        User existedUser = userRepository.findUserByUserName(authenRequest.getUserName());

        if (existedUser == null) {
            throw new AppException(ErrorCode.INVALID_USER_NOT_EXISTED);
        }

        if (new BCryptPasswordEncoder().matches(authenRequest.getPassword(), existedUser.getPassword())){
            return userMapper.mapperUserToUserResponse(existedUser);
        }else {
            throw new AppException(ErrorCode.INVALID_LOGIN_FAILED);
        }
        

    }
}
