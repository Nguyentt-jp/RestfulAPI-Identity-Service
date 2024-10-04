package identity_service.demo.dto.response;

import identity_service.demo.entity.Permission;
import identity_service.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String roleName;
    private String description;
    private Set<User> users;
    private Set<Permission> permissions;
}
