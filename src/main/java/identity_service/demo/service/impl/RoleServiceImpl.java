package identity_service.demo.service.impl;

import identity_service.demo.dto.request.RoleCreationRequest;
import identity_service.demo.dto.response.RoleResponse;
import identity_service.demo.entity.Permission;
import identity_service.demo.entity.Role;
import identity_service.demo.repository.PermissionRepository;
import identity_service.demo.repository.RoleRepository;
import identity_service.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Role createRole(RoleCreationRequest roleRequest) {
        Role role = new Role();
        role.setRoleName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getDescription());


        role.setPermissions(roleRequest.getPermissions());



        return roleRepository.save(role);
    }
}
