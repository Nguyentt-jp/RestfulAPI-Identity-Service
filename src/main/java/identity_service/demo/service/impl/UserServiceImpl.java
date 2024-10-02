package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.mapper.UserMapper;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreationUserRequest user) {

        User newUser = userMapper.mapperToUser(user);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsUserByUserName((user.getUserName()))) {
            throw new AppException(ErrorCode.INVALID_USER_EXISTED);
        }

        return userMapper.mapperUserToUserResponse(userRepository.save(newUser));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapperUserToUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(UUID id) {

        return userMapper.mapperUserToUserResponse(
                userRepository.findById(id).orElseThrow(
                        () -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND)
                )
        );
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest userUpdate) {
        User newUser = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));

        userMapper.mapperUpdateUserToUser(newUser,userUpdate);

        newUser.setPassword(new BCryptPasswordEncoder(10).encode(userUpdate.getPassword()));

        return userMapper.mapperUserToUserResponse(userRepository.save(newUser));
    }
}
