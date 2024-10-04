package identity_service.demo.controller;

import identity_service.demo.dto.request.PermissionCreationRequest;
import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.service.PermissionService;
import identity_service.demo.service.impl.PermissionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionServiceImpl permissionService;

    @PostMapping
    public ApiResponse<?> createPermission(PermissionCreationRequest permissionCreationRequest) {
        return ApiResponse.builder()
            .success(true)
            .result(permissionService.createPermission(permissionCreationRequest))
            .build();
    }
}
