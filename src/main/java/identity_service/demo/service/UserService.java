package identity_service.demo.service;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreationUserRequest user);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID id);
    void deleteUser(UUID id);
    UserResponse updateUser(UUID id, UpdateUserRequest user);

}
