package identity_service.demo.service;

import identity_service.demo.dto.request.CreationPermissionRequest;
import identity_service.demo.entity.Permission;

import java.util.List;

public interface PermissionService {
    Permission createPermission(CreationPermissionRequest roleCreationRequest);
    List<Permission> getAllPermissions();
}
