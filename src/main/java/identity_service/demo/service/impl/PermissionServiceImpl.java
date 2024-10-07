package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationPermissionRequest;
import identity_service.demo.entity.Permission;
import identity_service.demo.repository.PermissionRepository;
import identity_service.demo.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Permission createPermission(CreationPermissionRequest permissionCreationRequest) {
        Permission permission = Permission.builder()
            .permissionName(permissionCreationRequest.getPermissionName())
            .description(permissionCreationRequest.getDescription())
            .build();

        return permissionRepository.saveAndFlush(permission);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}
