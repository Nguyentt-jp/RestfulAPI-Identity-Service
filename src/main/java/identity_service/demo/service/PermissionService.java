package identity_service.demo.service;

import identity_service.demo.dto.request.PermissionCreationRequest;
import identity_service.demo.dto.response.PermissionResponse;
import identity_service.demo.entity.Permission;


public interface PermissionService {
    Permission createPermission(PermissionCreationRequest roleCreationRequest);
}
