package identity_service.demo.controller;

import identity_service.demo.dto.response.ApiResponse;
import identity_service.demo.dto.request.CreationUserRequest;
import identity_service.demo.dto.request.UpdateUserRequest;
import identity_service.demo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ApiResponse<Object> getAllUsers() {
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

    @PostMapping
    public ApiResponse<Object> createUser(@RequestBody @Valid CreationUserRequest userCreation) {
        return ApiResponse.builder()
                .success(true)
                .result(userService.createUser(userCreation))
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
}
