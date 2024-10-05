package identity_service.demo.dto.response;

import identity_service.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private UUID userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
