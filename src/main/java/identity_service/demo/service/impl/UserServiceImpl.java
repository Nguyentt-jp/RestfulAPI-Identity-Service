package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.Role;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.mapper.UserMapper;
import identity_service.demo.repository.RoleRepository;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse createUserByAdmin(CreationUserRequest user) {

        User newUser = userMapper.mapperToUser(user);

        var roles = roleRepository.findAllById(user.getRoles());

        newUser.setRoles(new HashSet<>(roles));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsUserByUserName((user.getUserName()))) {
            throw new AppException(ErrorCode.INVALID_USER_EXISTED);
        }

        var userResponse = userMapper.mapperUserToUserResponse(userRepository.save(newUser));
        userResponse.setRoles(new HashSet<>(roles));

        return userResponse;
    }

    @Override
    public UserResponse createUserByUser(CreationUserRequest user) {
        User newUser = userMapper.mapperToUser(user);

        var defaultRole = new ArrayList<String>();
        defaultRole.add("USER");

        var roles = roleRepository.findAllById(defaultRole);

        newUser.setRoles(new HashSet<>(roles));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsUserByUserName((user.getUserName()))) {
            throw new AppException(ErrorCode.INVALID_USER_EXISTED);
        }

        var userResponse = userMapper.mapperUserToUserResponse(userRepository.save(newUser));
        userResponse.setRoles(new HashSet<>(roles));

        return userResponse;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
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
    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUserById(UUID id) {
        return userMapper.mapperUserToUserResponse(
            userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND)
            )
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse updateUser(UUID id, UpdateUserRequest userUpdate) {
        User newUser = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));

        userMapper.mapperUpdateUserToUser(newUser,userUpdate);

        newUser.setPassword(new BCryptPasswordEncoder(10).encode(userUpdate.getPassword()));

        return userMapper.mapperUserToUserResponse(userRepository.save(newUser));
    }
}
