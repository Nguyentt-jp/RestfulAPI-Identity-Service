package identity_service.demo.service;

import identity_service.demo.dto.request.CreationRoleRequest;
import identity_service.demo.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(CreationRoleRequest roleCreationRequest);
    List<Role> getAllRoles();
}
