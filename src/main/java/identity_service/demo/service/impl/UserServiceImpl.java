package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.Role;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreationUserRequest user) {

        User newUser = userMapper.mapperToUser(user);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        newUser.setRoles(roles);


        if (userRepository.existsUserByUserName((user.getUserName()))) {
            throw new AppException(ErrorCode.INVALID_USER_EXISTED);
        }

        UserResponse userResponse = userMapper.mapperUserToUserResponse(userRepository.save(newUser));
        userResponse.setRoles(roles);

        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> {
                    UserResponse userResponse = userMapper.mapperUserToUserResponse(user);
                    userResponse.setRoles(user.getRoles());
                            return userResponse;
                        }
                )
                .toList();
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
