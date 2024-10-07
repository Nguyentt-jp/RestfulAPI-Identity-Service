package identity_service.demo.controller;

import identity_service.demo.dto.request.CreationRoleRequest;
import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping
    public ApiResponse<?> createRole(@RequestBody CreationRoleRequest roleCreationRequest) {
        return ApiResponse.builder()
            .success(true)
            .result(roleService.createRole(roleCreationRequest))
            .build();
    }

    @GetMapping
    public ApiResponse<?> getAllRoles() {
        return ApiResponse.builder()
            .success(true)
            .result(roleService.getAllRoles())
            .build();
    }
}
