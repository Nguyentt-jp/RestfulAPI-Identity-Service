package identity_service.demo.service.impl;

import identity_service.demo.dto.request.PermissionCreationRequest;
import identity_service.demo.dto.response.PermissionResponse;
import identity_service.demo.entity.Permission;
import identity_service.demo.repository.PermissionRepository;
import identity_service.demo.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(PermissionCreationRequest permissionCreationRequest) {
        Permission permission = Permission.builder()
            //.role(permissionCreationRequest.getRole())
            .description(permissionCreationRequest.getDescription())
            .build();


        return permissionRepository.save(permission);
    }
}
