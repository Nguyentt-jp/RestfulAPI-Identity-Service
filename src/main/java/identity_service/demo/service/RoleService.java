package identity_service.demo.service;

import identity_service.demo.dto.request.RoleCreationRequest;
import identity_service.demo.dto.response.RoleResponse;
import identity_service.demo.entity.Role;

public interface RoleService {
    Role createRole(RoleCreationRequest roleCreationRequest);
}
