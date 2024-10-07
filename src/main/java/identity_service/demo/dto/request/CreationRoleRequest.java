package identity_service.demo.dto.request;

import identity_service.demo.entity.Permission;
import identity_service.demo.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreationRoleRequest {
    @NotNull
    private String roleName;
    @NotNull
    private String description;

    @NotNull
    private Set<Permission> permissions;
}
