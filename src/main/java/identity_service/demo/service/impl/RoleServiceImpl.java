package identity_service.demo.service.impl;

import identity_service.demo.dto.request.CreationRoleRequest;
import identity_service.demo.entity.Role;
import identity_service.demo.exception.AppException;
import identity_service.demo.exception.ErrorCode;
import identity_service.demo.repository.PermissionRepository;
import identity_service.demo.repository.RoleRepository;
import identity_service.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Role createRole(CreationRoleRequest roleRequest) {
        if (roleRepository.findById(roleRequest.getRoleName()).isPresent()) {
            throw new AppException(ErrorCode.INVALID_ROLE_EXISTED);
        }
        Role role = new Role();
        role.setRoleName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getDescription());
        role.setPermissions(roleRequest.getPermissions());

        return roleRepository.saveAndFlush(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Role updateRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }
}
