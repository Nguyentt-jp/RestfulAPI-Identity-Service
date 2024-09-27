package identity_service.demo.mapper;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapperToUser(CreationUserRequest creationUserRequest);

    void mapperUpdateUserToUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

    UserResponse mapperUserToUserResponse(User user);
}
