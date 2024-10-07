package identity_service.demo.controller;

import identity_service.demo.dto.request.CreationPermissionRequest;
import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.service.impl.PermissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionServiceImpl permissionService;

    @PostMapping
    public ApiResponse<?> createPermission(@RequestBody CreationPermissionRequest permissionCreationRequest) {
        return ApiResponse.builder()
            .success(true)
            .result(permissionService.createPermission(permissionCreationRequest))
            .build();
    }

    @GetMapping
    public ApiResponse<?> getAllPermissions() {
        return ApiResponse.builder()
            .success(true)
            .result(permissionService.getAllPermissions())
            .build();
    }
}
