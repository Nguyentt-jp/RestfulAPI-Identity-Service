package identity_service.demo.dto.request;

import identity_service.demo.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionCreationRequest {
    private String permissionName;
    @NotNull
    private Role role;
    @NotNull
    private String description;
}
