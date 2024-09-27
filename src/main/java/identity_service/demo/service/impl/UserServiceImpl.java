package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.entity.User;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.repository.UserRepository;
import identity_service.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.String;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(CreationUserRequest user) {
        User newUser = new User();

        if (userRepository.existsUserByUserName((user.getUserName()))) {
            throw new AppException(ErrorCode.INVALID_USER_EXISTED);
        }

        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        return userRepository.save(newUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {

        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest userUpdate) {
        User newUser = getUserById(id);

        newUser.setPassword(userUpdate.getPassword());
        newUser.setFirstName(userUpdate.getFirstName());
        newUser.setLastName(userUpdate.getLastName());
        newUser.setEmail(userUpdate.getEmail());

        return userRepository.save(newUser);
    }


}
