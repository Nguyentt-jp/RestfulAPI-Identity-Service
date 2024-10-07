package identity_service.demo.mapper;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles" , ignore = true)
    User mapperToUser(CreationUserRequest creationUserRequest);

    void mapperUpdateUserToUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);

    @Mapping(target = "roles", ignore = true)
    UserResponse mapperUserToUserResponse(User user);
}
