package identity_service.demo.service;

import identity_service.demo.dto.request.PermissionCreationRequest;
import identity_service.demo.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreationRequest roleCreationRequest);
}
