package identity_service.demo.controller;

import identity_service.demo.dto.request.RoleCreationRequest;
import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping
    public ApiResponse<?> createRole(@RequestBody RoleCreationRequest roleCreationRequest) {

        return ApiResponse.builder()
            .success(true)
            .result(roleService.createRole(roleCreationRequest))
            .build();
    }
}
