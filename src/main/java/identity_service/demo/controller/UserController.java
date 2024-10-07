package identity_service.demo.controller;

import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.service.ViewService;
import identity_service.demo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final ViewService viewService;

    @GetMapping
    public ApiResponse<Object> getAllUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication: {}", authentication);
        log.info("User: {}", authentication.getName());

        authentication.getAuthorities().forEach(
            grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority.getAuthority())
            );

        return ApiResponse.builder()
            .success(true)
            .result(userService.getAllUsers())
            .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Object> getUserById(@PathVariable(value = "id") UUID id) {

        return ApiResponse.builder()
            .success(true)
            .result(userService.getUserById(id))
            .build();
    }

    @PostMapping("/admin")
    public ApiResponse<Object> createUserByAdmin(@RequestBody @Valid CreationUserRequest userCreation) {
        return ApiResponse.builder()
            .success(true)
            .result(userService.createUserByAdmin(userCreation))
            .build();
    }

    @PostMapping
    public ApiResponse<Object> createUserByClient(@RequestBody @Valid CreationUserRequest userCreation) {
        return ApiResponse.builder()
            .success(true)
            .result(userService.createUserByUser(userCreation))
            .build();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return "User " + id + " deleted!";
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserRequest userUpdate) {
        return ApiResponse.builder()
            .success(true)
            .result((userService.updateUser(id, userUpdate)))
            .build();
    }

    @GetMapping("/view")
    public String viewUser() {
        return viewService.view();
    }
}
