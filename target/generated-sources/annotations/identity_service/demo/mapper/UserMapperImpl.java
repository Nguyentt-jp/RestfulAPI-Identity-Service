package identity_service.demo.mapper;

import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.dto.response.UserResponse;
import identity_service.demo.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapperToUser(CreationUserRequest creationUserRequest) {
        if ( creationUserRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userName( creationUserRequest.getUserName() );
        user.password( creationUserRequest.getPassword() );
        user.firstName( creationUserRequest.getFirstName() );
        user.lastName( creationUserRequest.getLastName() );
        user.email( creationUserRequest.getEmail() );

        return user.build();
    }

    @Override
    public void mapperUpdateUserToUser(User user, UpdateUserRequest updateUserRequest) {
        if ( updateUserRequest == null ) {
            return;
        }

        user.setPassword( updateUserRequest.getPassword() );
        user.setFirstName( updateUserRequest.getFirstName() );
        user.setLastName( updateUserRequest.getLastName() );
        user.setEmail( updateUserRequest.getEmail() );
    }

    @Override
    public UserResponse mapperUserToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.userName( user.getUserName() );
        userResponse.password( user.getPassword() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.email( user.getEmail() );
        Set<String> set = user.getRoles();
        if ( set != null ) {
            userResponse.roles( new LinkedHashSet<String>( set ) );
        }

        return userResponse.build();
    }
}
