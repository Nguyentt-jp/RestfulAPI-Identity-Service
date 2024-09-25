package identity_service.demo.service;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(CreationUserRequest user);
    List<User> getAllUsers();
    User getUserById(UUID id);
    void deleteUser(UUID id);
    User updateUser(UUID id, UpdateUserRequest user);

}
